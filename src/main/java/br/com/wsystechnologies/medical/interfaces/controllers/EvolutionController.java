package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionRequest;
import br.com.wsystechnologies.medical.application.dto.evolution.EvolutionResponse;
import br.com.wsystechnologies.medical.application.services.EvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/evolutions")
@RequiredArgsConstructor
public class EvolutionController {

    private final EvolutionService service;

    @PostMapping
    public ResponseEntity<EvolutionResponse> create(@RequestBody EvolutionRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvolutionResponse> update(@PathVariable UUID id, @RequestBody EvolutionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvolutionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EvolutionResponse>> findAll(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID professionalId,
            @RequestParam(required = false) UUID clinicId) {
        if (patientId != null) {
            return ResponseEntity.ok(service.findAllByPatient(patientId));
        } else if (professionalId != null) {
            return ResponseEntity.ok(service.findAllByProfessional(professionalId));
        } else if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAll());
    }
}
