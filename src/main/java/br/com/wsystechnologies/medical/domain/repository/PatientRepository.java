package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findAllByClinicId(UUID clinicId);

    List<Patient> findByFullNameContainingIgnoreCase(String name);

    Optional<Patient> findByEmail(String email);
}
