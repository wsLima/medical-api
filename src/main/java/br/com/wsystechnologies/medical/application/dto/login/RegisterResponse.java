package br.com.wsystechnologies.medical.application.dto.login;

import java.util.List;
import java.util.UUID;

public record RegisterResponse(
        UUID userId,
        String name,
        String role,
        String email
) {}
