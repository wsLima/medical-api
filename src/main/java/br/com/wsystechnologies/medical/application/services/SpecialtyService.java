package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyRequest;
import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyResponse;

import java.util.List;
import java.util.UUID;

public interface SpecialtyService {
    SpecialtyResponse create(SpecialtyRequest request);
    SpecialtyResponse update(UUID id, SpecialtyRequest request);
    void delete(UUID id);
    SpecialtyResponse findById(UUID id);

    List<SpecialtyResponse> findAllByProfessional(UUID professionalId);

    List<SpecialtyResponse> findAll();
}

