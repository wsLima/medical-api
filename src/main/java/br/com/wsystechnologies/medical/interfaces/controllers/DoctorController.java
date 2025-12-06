package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.application.dto.doctor.DoctorRequest;
import br.com.wsystechnologies.medical.application.dto.doctor.DoctorResponse;
import br.com.wsystechnologies.medical.application.mapper.DoctorMapper;

import br.com.wsystechnologies.medical.application.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;
    private final DoctorMapper mapper;

    @PostMapping
    public ResponseEntity<DoctorResponse> create(@RequestBody @Valid DoctorRequest dto) {
        Professional professional = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/doctors/" + professional.getId()))
                .body(mapper.toDTO(professional));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> findAll() {
        List<DoctorResponse> list = service.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> findById(@PathVariable UUID id) {
        Professional professional = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(professional));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid DoctorRequest dto) {

        Professional updated = service.update(id, dto);
        return ResponseEntity.ok(mapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
