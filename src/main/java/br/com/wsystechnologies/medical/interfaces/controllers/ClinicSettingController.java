package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingRequest;
import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingResponse;
import br.com.wsystechnologies.medical.application.services.ClinicSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clinic-settings")
@RequiredArgsConstructor
public class ClinicSettingController {

    private final ClinicSettingService service;

    @PostMapping
    public ResponseEntity<ClinicSettingResponse> create(@RequestBody @Valid ClinicSettingRequest request) {
        ClinicSettingResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicSettingResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid ClinicSettingRequest request
    ) {
        ClinicSettingResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicSettingResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<ClinicSettingResponse>> findAllByClinic(@PathVariable UUID clinicId) {
        return ResponseEntity.ok(service.findAllByClinic(clinicId));
    }

    @GetMapping("/clinic/{clinicId}/key/{key}")
    public ResponseEntity<ClinicSettingResponse> findByClinicAndKey(
            @PathVariable UUID clinicId,
            @PathVariable String key
    ) {
        return ResponseEntity.ok(service.findByClinicAndKey(clinicId, key));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
