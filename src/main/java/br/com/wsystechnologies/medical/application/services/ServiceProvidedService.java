package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.serviceProvided.ServiceProvidedRequest;
import br.com.wsystechnologies.medical.application.dto.serviceProvided.ServiceProvidedResponse;

import java.util.List;
import java.util.UUID;

public interface ServiceProvidedService {
    ServiceProvidedResponse create(ServiceProvidedRequest request);

    ServiceProvidedResponse update(UUID id, ServiceProvidedRequest request);

    void delete(UUID id);

    ServiceProvidedResponse findById(UUID id);

    List<ServiceProvidedResponse> findAllByClinic(UUID clinicId);

    List<ServiceProvidedResponse> findAllActive();
}

