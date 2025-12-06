package br.com.wsystechnologies.medical.application.dto.medicalRecord;

import java.util.UUID;
import java.time.OffsetDateTime;

public record MedicalRecordRequest(
        UUID clinicId,
        UUID patientId,
        UUID createdBy,      // profissional responsável
        String mainIssue,
        String anamnesis,    // armazenado como JSON
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}