package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.person.PersonRequest;
import br.com.wsystechnologies.medical.application.dto.person.PersonResponse;
import br.com.wsystechnologies.medical.application.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @PostMapping
    public ResponseEntity<PersonResponse> create(@RequestBody PersonRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> update(@PathVariable UUID id, @RequestBody PersonRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf) {
//        if (name != null) {
//            return ResponseEntity.ok(service.findAllByFullName(name));
//        } else if (cpf != null) {
//            return ResponseEntity.ok(service.findAllByCpf(cpf));
//        }
        return ResponseEntity.ok(service.findAll());
    }
}

