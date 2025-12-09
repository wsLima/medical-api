package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.profile.ProfileRequest;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.application.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ProfileResponse> create(@RequestBody ProfileRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponse> update(@PathVariable UUID id, @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> findAll(
            @RequestParam(required = false) UUID clinicId,
            @RequestParam(required = false) String role) {
        if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        } else if (role != null) {
            return ResponseEntity.ok(service.findAllByRole(role));
        }
        return ResponseEntity.ok(service.findAll());
    }
}

