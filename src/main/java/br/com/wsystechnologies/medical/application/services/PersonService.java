package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.person.PersonRequest;
import br.com.wsystechnologies.medical.application.dto.person.PersonResponse;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonResponse create(PersonRequest request);

    PersonResponse update(UUID id, PersonRequest request);

    PersonResponse findById(UUID id);

    List<PersonResponse> findAll();

    void delete(UUID id);
}
