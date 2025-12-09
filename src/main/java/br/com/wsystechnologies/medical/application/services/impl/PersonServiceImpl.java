package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.person.PersonRequest;
import br.com.wsystechnologies.medical.application.dto.person.PersonResponse;
import br.com.wsystechnologies.medical.application.mapper.PersonMapper;
import br.com.wsystechnologies.medical.application.services.PersonService;
import br.com.wsystechnologies.medical.domain.model.Person;
import br.com.wsystechnologies.medical.domain.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Override
    @Transactional
    public PersonResponse create(PersonRequest request) {
        Person entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public PersonResponse update(UUID id, PersonRequest request) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public PersonResponse findById(UUID id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public List<PersonResponse> findAll() {
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
