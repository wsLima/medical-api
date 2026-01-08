package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordDocumentService {

    MedicalRecordDocumentResponse upload(UUID recordId, UUID profileId, MultipartFile file, String description);

    List<MedicalRecordDocumentResponse> listByRecord(UUID recordId);

    void delete(UUID id);
}
