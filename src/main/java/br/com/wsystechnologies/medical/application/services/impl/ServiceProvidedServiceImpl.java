package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.serviceProvided.ServiceProvidedRequest;
import br.com.wsystechnologies.medical.application.dto.serviceProvided.ServiceProvidedResponse;
import br.com.wsystechnologies.medical.application.mapper.ServiceProvidedMapper;
import br.com.wsystechnologies.medical.application.services.ServiceProvidedService;
import br.com.wsystechnologies.medical.domain.model.ServiceProvided;
import br.com.wsystechnologies.medical.domain.repository.ServiceProvidedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceProvidedServiceImpl implements ServiceProvidedService {

    private final ServiceProvidedRepository repository;
    private final ServiceProvidedMapper mapper;

    @Override
    public ServiceProvidedResponse create(ServiceProvidedRequest request) {
        ServiceProvided entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public ServiceProvidedResponse update(UUID id, ServiceProvidedRequest request) {
        ServiceProvided entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServiceProvided not found: " + id));
        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("ServiceProvided not found: " + id);
        repository.deleteById(id);
    }

    @Override
    public ServiceProvidedResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("ServiceProvided not found: " + id));
    }

    @Override
    public List<ServiceProvidedResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ServiceProvidedResponse> findAllActive() {
        return repository.findAllByActiveTrue()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}

