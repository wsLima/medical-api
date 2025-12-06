package br.com.wsystechnologies.medical.application.dto.login;

import br.com.wsystechnologies.medical.domain.enums.Role;

import java.util.UUID;

public record AuthResult(
        String accessToken,
        String refreshToken,
        UUID userId,
        String name,
        Role role,
        String email
) {}
