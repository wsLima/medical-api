package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Evolution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EvolutionRepository extends JpaRepository<Evolution, UUID> {

    List<Evolution> findAllByPatientId(UUID patientId);

    List<Evolution> findAllByProfessionalId(UUID professionalId);

    List<Evolution> findAllByClinicId(UUID clinicId);
}

