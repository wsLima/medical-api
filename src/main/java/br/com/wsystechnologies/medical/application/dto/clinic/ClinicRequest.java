package br.com.wsystechnologies.medical.application.dto.clinic;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClinicRequest {
    private String name;
    private String legalName;
    private String cnpj;
    private String phone;
    private String email;
    private UUID addressId;
}
