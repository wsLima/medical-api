package br.com.wsystechnologies.medical.application.dto.professional;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.domain.enums.Specialty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ProfessionalResponse extends BaseDTO {
    private ProfileResponse profile;
    private ClinicResponse clinic;

    private String crm;
    private Specialty specialty;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    private Boolean active;
}

