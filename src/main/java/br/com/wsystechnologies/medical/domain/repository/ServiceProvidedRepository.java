package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.ServiceProvided;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, UUID> {
    List<ServiceProvided> findAllByClinicId(UUID clinicId);

    List<ServiceProvided> findAllByActiveTrue();
}