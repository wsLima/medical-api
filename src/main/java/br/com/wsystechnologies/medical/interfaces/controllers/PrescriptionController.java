package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionRequest;
import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionResponse;
import br.com.wsystechnologies.medical.application.services.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService service;

    @PostMapping
    public ResponseEntity<PrescriptionResponse> create(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> update(@PathVariable UUID id,
                                                       @RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponse>> findByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(service.findAllByPatient(patientId));
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<PrescriptionResponse>> findByClinic(@PathVariable UUID clinicId) {
        return ResponseEntity.ok(service.findAllByClinic(clinicId));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<PrescriptionResponse>> findByProfessional(@PathVariable UUID professionalId) {
        return ResponseEntity.ok(service.findAllByProfessional(professionalId));
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
