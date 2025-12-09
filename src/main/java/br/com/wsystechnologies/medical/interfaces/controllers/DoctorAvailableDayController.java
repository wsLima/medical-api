package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayRequest;
import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayResponse;
import br.com.wsystechnologies.medical.application.services.DoctorAvailableDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor-available-days")
@RequiredArgsConstructor
public class DoctorAvailableDayController {

    private final DoctorAvailableDayService service;

    @PostMapping
    public ResponseEntity<DoctorAvailableDayResponse> create(@RequestBody DoctorAvailableDayRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> delete(@PathVariable UUID doctorId) {
        service.delete(doctorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailableDayResponse>> findByDoctor(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(service.findAllByDoctorId(doctorId));
    }
}
