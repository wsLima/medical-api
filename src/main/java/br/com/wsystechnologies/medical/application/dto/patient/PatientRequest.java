package br.com.wsystechnologies.medical.application.dto.patient;

import java.util.UUID;
import java.time.LocalDate;

public record PatientRequest(
        UUID clinicId,
        String fullName,
        LocalDate dateOfBirth,
        String documentId,
        String phone,
        String email,
        String address,   // pode ser JSON serializado
        String notes,
        UUID createdBy,
        boolean active
) {}