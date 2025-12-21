package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentRequest;
import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.application.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable UUID id, @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll(
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

    @PostMapping("/{id}/checkin")
    public ResponseEntity<AppointmentResponse> checkIn(@PathVariable UUID id, @RequestParam UUID userId) {
        return ResponseEntity.ok(service.checkIn(id, userId));
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<AppointmentResponse> checkOut(@PathVariable UUID id, @RequestParam UUID userId) {
        return ResponseEntity.ok(service.checkOut(id, userId));
    }
}

