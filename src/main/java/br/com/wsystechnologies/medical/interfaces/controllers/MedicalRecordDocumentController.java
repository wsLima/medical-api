package br.com.wsystechnologies.medical.interfaces.controllers;


import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentResponse;
import br.com.wsystechnologies.medical.application.services.MedicalRecordDocumentService;
import br.com.wsystechnologies.medical.infrastructure.storage.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medical-records/{recordId}/documents")
@RequiredArgsConstructor
public class MedicalRecordDocumentController {

    private final MedicalRecordDocumentService service;
    private final S3StorageService s3StorageService;

    @PostMapping("/upload")
    public ResponseEntity<MedicalRecordDocumentResponse> upload(
            @PathVariable UUID recordId,
            @RequestParam UUID profileId,
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false) String description) {

        return ResponseEntity.ok(service.upload(recordId, profileId, file, description));
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecordDocumentResponse>> list(@PathVariable UUID recordId) {
        return ResponseEntity.ok(service.listByRecord(recordId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
