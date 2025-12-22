package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordRequest;
import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordResponse;
import br.com.wsystechnologies.medical.application.mapper.MedicalRecordMapper;
import br.com.wsystechnologies.medical.application.services.MedicalRecordService;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.domain.model.MedicalRecord;
import br.com.wsystechnologies.medical.domain.model.Patient;
import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.domain.repository.ClinicRepository;
import br.com.wsystechnologies.medical.domain.repository.MedicalRecordRepository;
import br.com.wsystechnologies.medical.domain.repository.PatientRepository;
import br.com.wsystechnologies.medical.domain.repository.ProfessionalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    private final ProfessionalRepository professionalRepository;
    private final MedicalRecordMapper mapper;

    @Override
    public MedicalRecordResponse create(MedicalRecordRequest request) {
        MedicalRecord entity = mapper.toEntity(request);

        entity.setClinic(clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found: " + request.getClinicId())));
        entity.setPatient(patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + request.getPatientId())));
        entity.setCreatedBy(professionalRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new EntityNotFoundException("Professional not found: " + request.getCreatedById())));

        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public MedicalRecordResponse update(UUID id, MedicalRecordRequest request) {
        MedicalRecord entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MedicalRecord not found: " + id));

        mapper.updateEntityFromRequest(request, entity);

        // Atualiza relacionamentos caso IDs sejam enviados
        if (request.getClinicId() != null) {
            entity.setClinic(clinicRepository.findById(request.getClinicId())
                    .orElseThrow(() -> new EntityNotFoundException("Clinic not found: " + request.getClinicId())));
        }
        if (request.getPatientId() != null) {
            entity.setPatient(patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + request.getPatientId())));
        }
        if (request.getCreatedById() != null) {
            entity.setCreatedBy(professionalRepository.findById(request.getCreatedById())
                    .orElseThrow(() -> new EntityNotFoundException("Professional not found: " + request.getCreatedById())));
        }

        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("MedicalRecord not found: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public MedicalRecordResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("MedicalRecord not found: " + id));
    }

    @Override
    public List<MedicalRecordResponse> findAllByPatient(UUID patientId) {
        return repository.findAllByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<MedicalRecordResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
