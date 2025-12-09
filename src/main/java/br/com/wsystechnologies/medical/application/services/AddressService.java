package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.address.AddressRequest;
import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressResponse create(AddressRequest request);
    AddressResponse update(UUID id, AddressRequest request);
    AddressResponse findById(UUID id);
    List<AddressResponse> findAll();
    void delete(UUID id);
}
