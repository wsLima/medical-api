package br.com.wsystechnologies.medical.interfaces.advice;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String requestId
) {}
