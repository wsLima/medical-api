package br.com.wsystechnologies.medical.application.dto.profile;

import br.com.wsystechnologies.medical.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProfileRequest {
    private UUID clinicId;
    private UUID accountId;
    private Role role;
}