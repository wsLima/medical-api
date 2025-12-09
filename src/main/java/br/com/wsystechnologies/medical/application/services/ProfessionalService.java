package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalRequest;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ProfessionalService {

    ProfessionalResponse create(ProfessionalRequest request);

    ProfessionalResponse update(UUID id, ProfessionalRequest request);

    void delete(UUID id);

    ProfessionalResponse findById(UUID id);

    List<ProfessionalResponse> findAllByClinic(UUID clinicId);

    List<ProfessionalResponse> findAllBySpecialty(String specialty);

    List<ProfessionalResponse> findAll();
}

