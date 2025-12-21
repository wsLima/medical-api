package br.com.wsystechnologies.medical.application.dto.prescription;

import java.util.UUID;

public record PrescriptionRequest(
        UUID clinicId,
        UUID patientId,
        UUID professionalId,
        UUID appointmentId,
        String type,
        String description,
        String dosage,
        String frequency,
        String duration,
        String notes
) {}
