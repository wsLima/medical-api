package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpecialtyRepository extends JpaRepository<Specialty, UUID> {
    List<Specialty> findAllByProfessional(UUID professionalId);
    List<Specialty> findAllByTenantId(UUID tenantId);
}

