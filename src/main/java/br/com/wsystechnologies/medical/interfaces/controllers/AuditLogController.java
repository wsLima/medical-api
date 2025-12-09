package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogResponse;
import br.com.wsystechnologies.medical.application.services.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService service;

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID clinicId) {
        if (userId != null) {
            return ResponseEntity.ok(service.findAllByUser(userId));
        } else if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAll());
    }
}
