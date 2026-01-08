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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordDocumentServiceImpl implements MedicalRecordDocumentService {

    private final MedicalRecordDocumentRepository repository;
    private final MedicalRecordRepository recordRepository;
    private final ProfileRepository profileRepository;
    private final MedicalRecordDocumentMapper mapper;

    private final S3StorageService storageService;

    @Override
    public MedicalRecordDocumentResponse upload(UUID recordId, UUID profileId, MultipartFile file, String description) {
        MedicalRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new EntityNotFoundException("Medical record not found"));

        Profile uploader = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException("Uploader not found"));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String storagePath =  storageService.upload(file);

        MedicalRecordDocument entity = MedicalRecordDocument.builder()
                .tenantId(record.getTenantId())
                .medicalRecord(record)
                .uploadedBy(uploader)
                .fileName(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .sizeBytes(file.getSize())
                .storagePath(storagePath)
                .description(description)
                .createdAt(Instant.now())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public List<MedicalRecordDocumentResponse> listByRecord(UUID recordId) {
        return repository.findAllByMedicalRecordId(recordId).stream()
                .map(mapper::toResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Document not found");
        }
        repository.deleteById(id);
    }
}
