package br.com.wsystechnologies.medical.application.dto.appointment;

import java.util.UUID;
import java.time.OffsetDateTime;
import br.com.wsystechnologies.medical.domain.enums.AppointmentStatus;

public record AppointmentRequest(
        UUID id,
        UUID clinicId,
        UUID patientId,
        UUID professionalId,
        OffsetDateTime startsAt,
        OffsetDateTime endsAt,
        AppointmentStatus status,
        String notes,
        UUID createdBy
) {}
