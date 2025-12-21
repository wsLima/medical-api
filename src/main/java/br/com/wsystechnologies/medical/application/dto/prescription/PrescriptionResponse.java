package br.com.wsystechnologies.medical.application.dto.prescription;

import java.time.Instant;
import java.util.UUID;

public record PrescriptionResponse(
        UUID id,
        String type,
        String description,
        String dosage,
        String frequency,
        String duration,
        String notes,
        Instant createdAt
) {}