package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    List<MedicalRecord> findAllByPatientId(UUID patientId);

    List<MedicalRecord> findAllByClinicId(UUID clinicId);
}