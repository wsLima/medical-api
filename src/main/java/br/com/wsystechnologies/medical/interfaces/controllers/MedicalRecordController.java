package br.com.wsystechnologies.medical.interfaces.controllers;


import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordRequest;
import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordResponse;
import br.com.wsystechnologies.medical.application.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(@RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> update(@PathVariable UUID id,
                                                        @RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> findById(@PathVariable UUID id) {
        MedicalRecordResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> findAllByPatient(@PathVariable UUID patientId) {
        List<MedicalRecordResponse> records = service.findAllByPatient(patientId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<MedicalRecordResponse>> findAllByClinic(@PathVariable UUID clinicId) {
        List<MedicalRecordResponse> records = service.findAllByClinic(clinicId);
        return ResponseEntity.ok(records);
    }
}
