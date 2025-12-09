package br.com.wsystechnologies.medical.application.dto.evolution;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class EvolutionRequest {
    private UUID clinicId;
    private UUID patientId;
    private UUID professionalId;
    private UUID appointmentId;
    private String notes;
    private Map<String, Object> structuredData;
}