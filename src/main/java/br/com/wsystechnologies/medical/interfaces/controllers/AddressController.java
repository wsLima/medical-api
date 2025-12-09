package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.address.AddressRequest;
import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @PostMapping
    public ResponseEntity<AddressResponse> create(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@PathVariable UUID id, @RequestBody AddressRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll(@RequestParam(required = false) String city) {
//        if (city != null) {
//            return ResponseEntity.ok(service.findAllByCity(city));
//        }
        return ResponseEntity.ok(service.findAll());
    }
}
