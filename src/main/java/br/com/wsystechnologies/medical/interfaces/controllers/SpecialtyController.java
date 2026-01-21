package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyRequest;
import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyResponse;
import br.com.wsystechnologies.medical.application.services.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService service;

    @PostMapping
    public ResponseEntity<SpecialtyResponse> create(@RequestBody SpecialtyRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyResponse> update(@PathVariable UUID id,
                                                    @RequestBody SpecialtyRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<SpecialtyResponse>> findByProfessional(@PathVariable UUID professionalId) {
        return ResponseEntity.ok(service.findAllByProfessional(professionalId));
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

