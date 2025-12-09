package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogRequest;
import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface AuditLogService {

    AuditLogResponse create(AuditLogRequest request);

    AuditLogResponse findById(UUID id);

    List<AuditLogResponse> findAllByUser(UUID userId);

    List<AuditLogResponse> findAllByClinic(UUID clinicId);

    List<AuditLogResponse> findAll();
}
