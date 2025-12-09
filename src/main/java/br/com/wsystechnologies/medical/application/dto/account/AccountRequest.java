package br.com.wsystechnologies.medical.application.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AccountRequest {
    private UUID clinicId;
    private UUID personId;
    private UUID addressId;
    private String email;
    private String password;
}
