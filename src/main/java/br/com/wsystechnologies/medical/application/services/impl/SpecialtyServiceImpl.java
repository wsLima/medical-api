package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyRequest;
import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyResponse;
import br.com.wsystechnologies.medical.application.mapper.SpecialtyMapper;
import br.com.wsystechnologies.medical.application.services.SpecialtyService;
import br.com.wsystechnologies.medical.domain.model.Specialty;
import br.com.wsystechnologies.medical.domain.repository.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    @Override
    public SpecialtyResponse create(SpecialtyRequest request) {
        Specialty entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public SpecialtyResponse update(UUID id, SpecialtyRequest request) {
        Specialty existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));
        mapper.updateEntityFromRequest(request, existing);
        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public SpecialtyResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));
    }

    @Override
    public List<SpecialtyResponse> findAllByProfessional(UUID professionalId) {
        return repository.findAllByProfessional(professionalId).stream()
                .map(mapper::toResponse).toList();
    }

    @Override
    public List<SpecialtyResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse).toList();
    }
}

