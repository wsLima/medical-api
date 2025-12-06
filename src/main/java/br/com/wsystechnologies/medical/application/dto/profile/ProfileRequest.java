package br.com.wsystechnologies.medical.application.dto.profile;

import br.com.wsystechnologies.medical.domain.enums.Role;

import java.util.UUID;

public record ProfileRequest(
        UUID clinicId,
        String fullName,
        Role role,
        boolean active
) {}