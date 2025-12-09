package br.com.wsystechnologies.medical.application.dto.address;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse extends BaseDTO {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
    private String type;
}