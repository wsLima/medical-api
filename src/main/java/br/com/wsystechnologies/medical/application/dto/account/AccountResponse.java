package br.com.wsystechnologies.medical.application.dto.account;

import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.person.PersonResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AccountResponse extends BaseDTO {
    private ClinicResponse clinic;
    private PersonResponse person;
    private AddressResponse address;

    private String email;
    private Boolean enabled;
    private Instant lastLogin;
}
