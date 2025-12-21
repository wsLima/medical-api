package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionRequest;
import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionResponse;

import java.util.List;
import java.util.UUID;

public interface PrescriptionService {

    PrescriptionResponse create(PrescriptionRequest request);

    PrescriptionResponse update(UUID id, PrescriptionRequest request);

    void delete(UUID id);

    PrescriptionResponse findById(UUID id);

    List<PrescriptionResponse> findAllByPatient(UUID patientId);

    List<PrescriptionResponse> findAllByClinic(UUID clinicId);

    List<PrescriptionResponse> findAllByProfessional(UUID professionalId);

    List<PrescriptionResponse> findAll();
}
