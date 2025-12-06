package br.com.wsystechnologies.medical.application.dto.professional;

import br.com.wsystechnologies.medical.domain.enums.Specialty;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record ProfessionalResponse(
        UUID id,
        UUID clinicId,
        String crm,
        Specialty specialty,
        List<Integer> availableDays,
        LocalTime availableFrom,
        LocalTime availableTo,
        boolean active
) {
}
