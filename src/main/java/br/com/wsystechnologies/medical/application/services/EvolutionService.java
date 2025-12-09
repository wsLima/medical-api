package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionRequest;
import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface EvolutionService {

    EvolutionResponse create(EvolutionRequest request);

    EvolutionResponse update(UUID id, EvolutionRequest request);

    void delete(UUID id);

    EvolutionResponse findById(UUID id);

    List<EvolutionResponse> findAllByPatient(UUID patientId);

    List<EvolutionResponse> findAllByProfessional(UUID professionalId);

    List<EvolutionResponse> findAllByClinic(UUID clinicId);

    List<EvolutionResponse> findAll();
}
