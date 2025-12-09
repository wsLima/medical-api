package br.com.wsystechnologies.medical.application.dto.profile;

import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse extends BaseDTO {
    private ClinicResponse clinic;
    private AccountResponse account;
    private Role role;
    private Boolean active;
}