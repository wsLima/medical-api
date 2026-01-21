package br.com.wsystechnologies.medical.application.dto.specialty;

import java.time.Instant;
import java.util.UUID;

public record SpecialtyResponse(
        UUID id,
        String name,
        String description,
        Instant createdAt
) {}
