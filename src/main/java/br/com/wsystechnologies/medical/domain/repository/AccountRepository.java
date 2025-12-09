package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByEmail(String email);

    List<Account> findByClinicId(UUID clinicId);

    Optional<Account> findByPersonId(UUID personId);
    List<Account> findAllByClinicId(UUID clinicId);
    List<Account> findAllByPersonId(UUID personId);
}
