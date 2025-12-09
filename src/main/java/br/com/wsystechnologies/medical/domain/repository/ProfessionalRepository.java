package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.enums.Specialty;
import br.com.wsystechnologies.medical.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository extends JpaRepository<Professional, UUID> {
    Optional<Professional> findByCrm(String crm);

    List<Professional> findByClinicId(UUID clinicId);

    List<Professional> findBySpecialty(Specialty specialty);

    List<Professional> findAllByClinicId(UUID clinicId);

    List<Professional> findAllBySpecialty(String specialty);


}
