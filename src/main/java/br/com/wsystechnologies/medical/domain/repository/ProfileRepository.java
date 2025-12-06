package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.domain.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findById(UUID id);

    Page<Profile> findByClinic_Id(UUID clinicId, Pageable pageable);

    List<Profile> findByClinic_IdAndIsActiveTrue(UUID clinicId);

    List<Profile> findByClinic_IdAndRole(UUID clinicId, Role role);

    Optional<Profile> findByUser_Email(String email);
}