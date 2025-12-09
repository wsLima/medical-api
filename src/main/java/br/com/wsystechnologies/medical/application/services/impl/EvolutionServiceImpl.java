package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionRequest;
import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionResponse;
import br.com.wsystechnologies.medical.application.mapper.EvolutionMapper;
import br.com.wsystechnologies.medical.application.services.EvolutionService;
import br.com.wsystechnologies.medical.domain.model.Evolution;
import br.com.wsystechnologies.medical.domain.repository.EvolutionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EvolutionServiceImpl implements EvolutionService {

    private final EvolutionRepository repository;
    private final EvolutionMapper mapper;

    @Override
    public EvolutionResponse create(EvolutionRequest request) {
        Evolution entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public EvolutionResponse update(UUID id, EvolutionRequest request) {
        Evolution entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evolution not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Evolution not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public EvolutionResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Evolution not found: " + id));
    }

    @Override
    public List<EvolutionResponse> findAllByPatient(UUID patientId) {
        return repository.findAllByPatientId(patientId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<EvolutionResponse> findAllByProfessional(UUID professionalId) {
        return repository.findAllByProfessionalId(professionalId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<EvolutionResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<EvolutionResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
