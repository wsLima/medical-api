package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.patient.PatientRequest;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientResponse create(PatientRequest request);

    PatientResponse update(UUID id, PatientRequest request);

    void delete(UUID id);

    PatientResponse findById(UUID id);

    List<PatientResponse> findAllByClinic(UUID clinicId);

    List<PatientResponse> findAllByName(String name);

    List<PatientResponse> findAll();

    Page<PatientResponse> search(String fullName, String documentId, String email, Pageable pageable);
}
