package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.account.AccountRequest;
import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;
import br.com.wsystechnologies.medical.application.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable UUID id, @RequestBody AccountRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll(@RequestParam(required = false) UUID clinicId) {
        if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAllByClinic(clinicId));
//        return ResponseEntity.ok(service.findAll());
    }
}
