package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogRequest;
import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogResponse;
import br.com.wsystechnologies.medical.application.mapper.AuditLogMapper;
import br.com.wsystechnologies.medical.application.services.AuditLogService;
import br.com.wsystechnologies.medical.domain.model.AuditLog;
import br.com.wsystechnologies.medical.domain.repository.AuditLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository repository;
    private final AuditLogMapper mapper;

    @Override
    public AuditLogResponse create(AuditLogRequest request) {
        AuditLog entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public AuditLogResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("AuditLog not found: " + id));
    }

    @Override
    public List<AuditLogResponse> findAllByUser(UUID userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
