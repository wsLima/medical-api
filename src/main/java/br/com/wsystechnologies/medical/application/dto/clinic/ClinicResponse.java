package br.com.wsystechnologies.medical.application.dto.clinic;

import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicResponse extends BaseDTO {
    private String name;
    private String legalName;
    private String cnpj;
    private String phone;
    private String email;

    private AddressResponse address;
    private Boolean active;
}

