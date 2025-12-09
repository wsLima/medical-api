package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.address.AddressRequest;
import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.mapper.AddressMapper;
import br.com.wsystechnologies.medical.application.services.AddressService;
import br.com.wsystechnologies.medical.domain.model.Address;
import br.com.wsystechnologies.medical.domain.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    @Override
    @Transactional
    public AddressResponse create(AddressRequest request) {
        Address entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public AddressResponse update(UUID id, AddressRequest request) {
        Address entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + id));

        mapper.updateEntityFromDto(request, entity);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public AddressResponse findById(UUID id) {
        Address entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public List<AddressResponse> findAll() {
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