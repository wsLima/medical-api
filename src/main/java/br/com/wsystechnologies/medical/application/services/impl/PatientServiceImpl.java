package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.patient.PatientRequest;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.mapper.PatientMapper;
import br.com.wsystechnologies.medical.application.services.PatientService;
import br.com.wsystechnologies.medical.domain.model.Patient;
import br.com.wsystechnologies.medical.domain.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    public PatientResponse create(PatientRequest request) {
        Patient entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public PatientResponse update(UUID id, PatientRequest request) {
        Patient entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Patient not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public PatientResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    @Override
    public List<PatientResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PatientResponse> findAllByName(String name) {
        return repository.findByFullNameContainingIgnoreCase(name).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PatientResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
