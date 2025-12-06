package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Professional, UUID> {
    boolean existsByCrmIgnoreCase(String crm);
}