package br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments;

import java.util.UUID;

public record MedicalRecordDocumentRequest(
        UUID medicalRecordId,
        String fileName,
        String mimeType,
        long sizeBytes,
        String description
) {}
