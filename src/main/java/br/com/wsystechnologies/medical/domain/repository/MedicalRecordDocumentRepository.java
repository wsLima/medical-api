package br.com.wsystechnologies.medical.domain.repository;


import br.com.wsystechnologies.medical.domain.model.MedicalRecordDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordDocumentRepository extends JpaRepository<MedicalRecordDocument, UUID> {

    List<MedicalRecordDocument> findAllByMedicalRecordId(UUID medicalRecordId);

    List<MedicalRecordDocument> findAllByTenantId(UUID tenantId);
}

