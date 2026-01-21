package br.com.wsystechnologies.medical.application.dto.specialty;

import java.util.UUID;

public record SpecialtyRequest(
        UUID clinicId,
        String name,
        String description
) {}
