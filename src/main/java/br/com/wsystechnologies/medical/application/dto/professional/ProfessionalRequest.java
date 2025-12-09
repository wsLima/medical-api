package br.com.wsystechnologies.medical.application.dto.professional;

import br.com.wsystechnologies.medical.domain.enums.Specialty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class ProfessionalRequest {
    private UUID profileId;
    private UUID clinicId;
    private String crm;
    private Specialty specialty;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
