package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalRequest;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import br.com.wsystechnologies.medical.application.services.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService service;

    @PostMapping
    public ResponseEntity<ProfessionalResponse> create(@RequestBody ProfessionalRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> update(@PathVariable UUID id, @RequestBody ProfessionalRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalResponse>> findAll(
            @RequestParam(required = false) UUID clinicId,
            @RequestParam(required = false) String specialty) {
        if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        } else if (specialty != null) {
            return ResponseEntity.ok(service.findAllBySpecialty(specialty));
        }
        return ResponseEntity.ok(service.findAll());
    }
}

