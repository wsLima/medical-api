package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.document.DocumentRequest;
import br.com.wsystechnologies.medical.application.dto.document.DocumentResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface DocumentService {

    DocumentResponse create(DocumentRequest request);

    DocumentResponse update(UUID id, DocumentRequest request);

    void delete(UUID id);

    DocumentResponse findById(UUID id);

    List<DocumentResponse> findAllByPatient(UUID patientId);

    List<DocumentResponse> findAllByClinic(UUID clinicId);

    List<DocumentResponse> findAll();
}
