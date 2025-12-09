package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.clinic.ClinicRequest;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicService {
    ClinicResponse create(ClinicRequest request);

    ClinicResponse update(UUID id, ClinicRequest request);

    ClinicResponse findById(UUID id);

    List<ClinicResponse> findAll();

    void delete(UUID id);
}
