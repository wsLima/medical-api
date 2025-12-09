package br.com.wsystechnologies.medical.application.dto.auditLog;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AuditLogResponse extends BaseDTO {
    private ClinicResponse clinic;
    private ProfileResponse user;

    private String entityType;
    private UUID entityId;
    private String action;
    private Map<String, Object> details;
}