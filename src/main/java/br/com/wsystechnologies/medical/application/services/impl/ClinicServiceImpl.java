package br.com.wsystechnologies.medical.application.services.impl;


import br.com.wsystechnologies.medical.application.dto.clinic.ClinicRequest;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.mapper.ClinicMapper;
import br.com.wsystechnologies.medical.application.services.ClinicService;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.domain.repository.ClinicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository repository;
    private final ClinicMapper mapper;

    @Override
    @Transactional
    public ClinicResponse create(ClinicRequest request) {
        Clinic entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public ClinicResponse update(UUID id, ClinicRequest request) {
        Clinic entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public ClinicResponse findById(UUID id) {
        Clinic entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    public List<ClinicResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
