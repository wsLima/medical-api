package br.com.wsystechnologies.medical.application.dto.appointment;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {
    private UUID clinicId;
    private UUID patientId;
    private UUID professionalId;
    private UUID serviceProvidedId;
    private Instant startsAt;
    private Instant endsAt;
    private String notes;
}
