// java
    package br.com.wsystechnologies.medical.application.services.impl;

    import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentResponse;
    import br.com.wsystechnologies.medical.application.mapper.MedicalRecordDocumentMapper;
    import br.com.wsystechnologies.medical.application.services.MedicalRecordDocumentService;
    import br.com.wsystechnologies.medical.domain.model.MedicalRecord;
    import br.com.wsystechnologies.medical.domain.model.MedicalRecordDocument;
    import br.com.wsystechnologies.medical.domain.model.Profile;
    import br.com.wsystechnologies.medical.domain.repository.MedicalRecordDocumentRepository;
    import br.com.wsystechnologies.medical.domain.repository.MedicalRecordRepository;
    import br.com.wsystechnologies.medical.domain.repository.ProfileRepository;
    import br.com.wsystechnologies.medical.infrastructure.storage.S3StorageService;
    import jakarta.persistence.EntityNotFoundException;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.multipart.MultipartFile;

    import java.time.Instant;
    import java.util.List;
    import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class MedicalRecordDocumentServiceImpl implements MedicalRecordDocumentService {

        private static final long MAX_DESCRIPTION_LENGTH = 1024;
        private static final String DEFAULT_MIME = "application/octet-stream";

        private final S3StorageService storageService;

        private final MedicalRecordDocumentRepository repository;
        private final MedicalRecordRepository recordRepository;
        private final ProfileRepository profileRepository;

        private final MedicalRecordDocumentMapper mapper;



        @Override
        @Transactional
        public MedicalRecordDocumentResponse upload(UUID recordId, UUID profileId, MultipartFile file, String description) {
            MedicalRecord record = recordRepository.findById(recordId)
                    .orElseThrow(() -> new EntityNotFoundException("Medical record not found"));

            Profile uploader = profileRepository.findById(profileId)
                    .orElseThrow(() -> new EntityNotFoundException("Uploader not found"));

            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("Description is too long");
            }

            if (record.getTenantId() == null || uploader.getTenantId() == null ||
                    !record.getTenantId().equals(uploader.getTenantId())) {
                throw new IllegalArgumentException("Uploader does not belong to the same tenant as the record");
            }

            String originalFileName = file.getOriginalFilename();
            String mimeType = file.getContentType() != null ? file.getContentType() : DEFAULT_MIME;

            String storagePath;
            try {
                storagePath = storageService.upload(file);
            } catch (Exception ex) {
                log.error("Failed to upload file to storage", ex);
                throw new IllegalStateException("Failed to upload file", ex);
            }

            MedicalRecordDocument entity = MedicalRecordDocument.builder()
                    .tenantId(record.getTenantId())
                    .medicalRecord(record)
                    .uploadedBy(uploader)
                    .fileName(originalFileName)
                    .mimeType(mimeType)
                    .sizeBytes(file.getSize())
                    .storagePath(storagePath)
                    .description(description)
                    .createdAt(Instant.now())
                    .build();

            try {
                MedicalRecordDocument saved = repository.save(entity);
                return mapper.toResponse(saved);
            } catch (RuntimeException ex) {
                // tenta limpar o arquivo enviado se o save falhar
                try {
                    storageService.delete(storagePath);
                } catch (Exception deleteEx) {
                    log.warn("Failed to delete uploaded file after DB save failure: {}", storagePath, deleteEx);
                }
                log.error("Failed to save document entity", ex);
                throw ex;
            }
        }

        @Override
        @Transactional(readOnly = true)
        public List<MedicalRecordDocumentResponse> listByRecord(UUID recordId) {
            return repository.findAllByMedicalRecordId(recordId).stream()
                    .map(mapper::toResponse).toList();
        }

        @Override
        @Transactional
        public void delete(UUID id) {
            MedicalRecordDocument doc = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Document not found"));

            String storagePath = doc.getStoragePath();

            try {
                // primeiro remove do storage
                if (storagePath != null) {
                    storageService.delete(storagePath);
                }
            } catch (Exception ex) {
                log.error("Failed to delete file from storage for document id {}", id, ex);
                throw new IllegalStateException("Failed to delete file from storage", ex);
            }

            repository.deleteById(id);
        }
    }