package br.com.wsystechnologies.medical.application.dto.person;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PersonRequest {
    private String fullName;
    private String cpf;
    private String rg;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;
    private UUID addressId;
}
