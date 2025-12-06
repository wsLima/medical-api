package br.com.wsystechnologies.medical.application.dto.evolution;

import java.util.UUID;
import java.time.OffsetDateTime;

public record EvolutionRequest(
        UUID clinicId,
        UUID patientId,
        UUID professionalId,
        UUID appointmentId,
        String notes,
        String structuredData,   // armazenado como JSON
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}