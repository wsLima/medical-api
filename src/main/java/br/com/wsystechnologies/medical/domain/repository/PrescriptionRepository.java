package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    List<Prescription> findAllByPatientId(UUID patientId);

    List<Prescription> findAllByClinicId(UUID clinicId);

    List<Prescription> findAllByProfessionalId(UUID professionalId);
}
