package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalRequest;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import br.com.wsystechnologies.medical.application.mapper.ProfessionalMapper;
import br.com.wsystechnologies.medical.application.services.ProfessionalService;
import br.com.wsystechnologies.medical.domain.model.Professional;
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
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository repository;
    private final ProfessionalMapper mapper;

    @Override
    public ProfessionalResponse create(ProfessionalRequest request) {
        Professional entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public ProfessionalResponse update(UUID id, ProfessionalRequest request) {
        Professional entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Professional not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public ProfessionalResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Professional not found: " + id));
    }

    @Override
    public List<ProfessionalResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProfessionalResponse> findAllBySpecialty(String specialty) {
        return repository.findAllBySpecialty(specialty)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProfessionalResponse> findAll() {
        return repository.findAll().stream().map(professional ->  mapper.toResponse(professional)).toList();
    }
}
