package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.patient.PatientRequest;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable UUID id, @RequestBody PatientRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll(
            @RequestParam(required = false) UUID clinicId,
            @RequestParam(required = false) String name) {
        if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        } else if (name != null) {
            return ResponseEntity.ok(service.findAllByName(name));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/search")
    public Page<PatientResponse> search(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String documentId,
            @RequestParam(required = false) String email,
            Pageable pageable
    ) {
        return service.search(fullName, documentId, email, pageable);
    }
}

