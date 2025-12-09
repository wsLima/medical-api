package br.com.wsystechnologies.medical.application.dto.person;

import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonResponse extends BaseDTO {
    private String fullName;
    private String cpf;
    private String rg;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private AddressResponse address;
    private Boolean active;
}
