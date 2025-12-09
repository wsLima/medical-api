package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordRequest;
import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordResponse;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordService {

    MedicalRecordResponse create(MedicalRecordRequest request);

    MedicalRecordResponse update(UUID id, MedicalRecordRequest request);

    void delete(UUID id);

    MedicalRecordResponse findById(UUID id);

    List<MedicalRecordResponse> findAllByPatient(UUID patientId);

    List<MedicalRecordResponse> findAllByClinic(UUID clinicId);
}
