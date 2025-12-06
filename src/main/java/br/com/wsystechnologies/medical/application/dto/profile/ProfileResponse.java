package br.com.wsystechnologies.medical.application.dto.profile;

import br.com.wsystechnologies.medical.domain.enums.Role;

import java.util.UUID;

public record ProfileResponse(
        UUID id,
        UUID clinicId,
        String fullName,
        Role role,
        boolean active
) {}