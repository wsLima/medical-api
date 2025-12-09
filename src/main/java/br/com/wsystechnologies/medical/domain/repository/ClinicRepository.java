package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {
    boolean existsByCnpj(String cnpj);
    Optional<Clinic> findByCnpj(String cnpj);
    Optional<Clinic> findByName(String name);
    List<Clinic> findAllByNameContainingIgnoreCase(String name);
}
