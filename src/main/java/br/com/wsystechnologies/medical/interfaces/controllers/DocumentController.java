package br.com.wsystechnologies.medical.interfaces.controllers;


import br.com.wsystechnologies.medical.application.dto.document.DocumentRequest;
import br.com.wsystechnologies.medical.application.dto.document.DocumentResponse;
import br.com.wsystechnologies.medical.application.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping
    public ResponseEntity<DocumentResponse> create(@RequestBody DocumentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponse> update(@PathVariable UUID id, @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> findAll(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID clinicId) {
        if (patientId != null) {
            return ResponseEntity.ok(service.findAllByPatient(patientId));
        } else if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAll());
    }
}
