package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.domain.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository extends JpaRepository<Professional, UUID> {
    List<Professional> findByClinic_Id(UUID clinicId);
    List<Professional> findBySpecialty(Specialty specialty);
    Optional<Professional> findByCrm(String crm);
}
