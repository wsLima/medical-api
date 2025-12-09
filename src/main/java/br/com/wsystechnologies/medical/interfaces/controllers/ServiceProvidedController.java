package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.ServiceProvided.ServiceProvidedRequest;
import br.com.wsystechnologies.medical.application.dto.ServiceProvided.ServiceProvidedResponse;
import br.com.wsystechnologies.medical.application.services.ServiceProvidedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services-provided")
@RequiredArgsConstructor
public class ServiceProvidedController {

    private final ServiceProvidedService service;

    @PostMapping
    public ResponseEntity<ServiceProvidedResponse> create(@RequestBody ServiceProvidedRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceProvidedResponse> update(@PathVariable UUID id, @RequestBody ServiceProvidedRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProvidedResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServiceProvidedResponse>> findAll(@RequestParam(required = false) UUID clinicId) {
        if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAllActive());
    }
}
