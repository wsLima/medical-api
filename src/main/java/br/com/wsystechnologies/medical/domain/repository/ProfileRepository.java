package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    List<Profile> findByClinicId(UUID clinicId);

    List<Profile> findByRole(Role role);

    Optional<Profile> findByAccountId(UUID accountId);

    List<Profile> findAllByClinicId(UUID clinicId);

    List<Profile> findAllByRole(String role);
}