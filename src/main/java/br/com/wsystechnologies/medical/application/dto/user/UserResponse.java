package br.com.wsystechnologies.medical.application.dto.user;

import java.util.UUID;

public record UserResponse (
    UUID id,
    String email,
    boolean enabled
) {}
