package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    List<Patient> findAllByClinicId(UUID clinicId);

    List<Patient> findByFullNameContainingIgnoreCase(String name);

    Optional<Patient> findByEmail(String email);

    @Query("""
        SELECT p FROM Patient p
        WHERE (:fullName IS NULL OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :fullName, '%')))
          AND (:documentId IS NULL OR p.documentId = :documentId)
          AND (:email IS NULL OR p.email = :email)
    """)
    Page<Patient> search(
            @Param("fullName") String fullName,
            @Param("documentId") String documentId,
            @Param("email") String email,
            Pageable pageable
    );
}
