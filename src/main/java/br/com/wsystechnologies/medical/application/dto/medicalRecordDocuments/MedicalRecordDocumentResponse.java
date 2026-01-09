package br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments;

import java.time.Instant;
import java.util.UUID;

public record MedicalRecordDocumentResponse(
        UUID id,
        String fileName,
        String mimeType,
        long sizeBytes,
        String description,
        Instant createdAt,
        String storagePath
) {}
