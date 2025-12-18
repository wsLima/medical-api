package br.com.wsystechnologies.medical.application.dto.clinicSetting;

import br.com.wsystechnologies.medical.domain.enums.ClinicSettingType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ClinicSettingRequest {

    private UUID clinicId;
    private String key;
    private String value;
    private ClinicSettingType type;
    private String description;
}
