package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.document.DocumentRequest;
import br.com.wsystechnologies.medical.application.dto.document.DocumentResponse;
import br.com.wsystechnologies.medical.application.mapper.DocumentMapper;
import br.com.wsystechnologies.medical.application.services.DocumentService;
import br.com.wsystechnologies.medical.application.services.PatientService;
import br.com.wsystechnologies.medical.domain.model.*;
import br.com.wsystechnologies.medical.domain.repository.*;
import br.com.wsystechnologies.medical.exceptions.BusinessException;
import br.com.wsystechnologies.medical.infrastructure.storage.S3StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;
    private final PatientRepository patientRepository;
    private final ProfileRepository profileRepository;
    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;

    private final S3StorageService storageService;
    private final DocumentMapper mapper;

    @Override
    @Transactional
    public DocumentResponse create(DocumentRequest request) {
        // Upload para S3
        String key = storageService.upload(request.getFile());

        // Monta entidade
        Document entity = mapper.toEntity(request);
        entity.setStoragePath(key);

        // Garantir relacionamentos obrigatórios
        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new BusinessException("Clinic not found"));
        entity.setClinic(clinic);

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new BusinessException("Patient not found"));
        entity.setPatient(patient);

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new BusinessException("Appointment not found"));
        entity.setAppointment(appointment);

        Profile createdBy = profileRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new BusinessException("Profile not found"));
        entity.setCreatedBy(createdBy);

        // Salva no banco
        repository.save(entity);

        return mapper.toResponse(entity);
    }


    @Override
    public DocumentResponse update(UUID id, DocumentRequest request) {
        Document entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Document not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public DocumentResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Document not found: " + id));
    }

    @Override
    public List<DocumentResponse> findAllByPatient(UUID patientId) {
        return repository.findAllByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<DocumentResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<DocumentResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
