package br.com.wsystechnologies.medical.application.dto.auditLog;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class AuditLogRequest {
    private UUID clinicId;
    private UUID userId;          // profile id
    private String entityType;
    private UUID entityId;
    private String action;
    private Map<String, Object> details;
}