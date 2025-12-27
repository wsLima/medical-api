package br.com.wsystechnologies.medical.interfaces.controllers;


import br.com.wsystechnologies.medical.application.dto.document.DocumentRequest;
import br.com.wsystechnologies.medical.application.dto.document.DocumentResponse;
import br.com.wsystechnologies.medical.application.services.DocumentService;
import br.com.wsystechnologies.medical.domain.enums.DocumentType;
import br.com.wsystechnologies.medical.infrastructure.storage.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;
    private final S3StorageService s3StorageService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DocumentResponse> upload(
            @RequestParam("clinicId") UUID clinicId,
            @RequestParam("patientId") UUID patientId,
            @RequestParam(value = "appointmentId", required = false) UUID appointmentId,
            @RequestParam("createdById") UUID createdById,
            @RequestParam("type") String type,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("file") MultipartFile file
    ) {
        DocumentRequest request = new DocumentRequest();
        request.setClinicId(clinicId);
        request.setPatientId(patientId);
        request.setAppointmentId(appointmentId);
        request.setCreatedById(createdById);
        request.setType(DocumentType.valueOf(type));
        request.setName(name);
        request.setDescription(description);
        request.setFile(file);

        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<String> download(@PathVariable UUID id) {
        DocumentResponse doc = service.findById(id);
        String url = s3StorageService.generateDownloadUrl(doc.getStoragePath());
        return ResponseEntity.ok(url);
    }



    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponse> update(@PathVariable UUID id, @RequestBody DocumentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> findAll(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID clinicId) {
        if (patientId != null) {
            return ResponseEntity.ok(service.findAllByPatient(patientId));
        } else if (clinicId != null) {
            return ResponseEntity.ok(service.findAllByClinic(clinicId));
        }
        return ResponseEntity.ok(service.findAll());
    }
}
