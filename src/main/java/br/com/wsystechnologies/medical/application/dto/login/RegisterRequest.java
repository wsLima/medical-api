package br.com.wsystechnologies.medical.application.dto.login;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String role
) {}
