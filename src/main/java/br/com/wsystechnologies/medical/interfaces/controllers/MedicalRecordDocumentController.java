package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentResponse;
import br.com.wsystechnologies.medical.application.services.MedicalRecordDocumentService;
import br.com.wsystechnologies.medical.exceptions.BusinessException;
import br.com.wsystechnologies.medical.infrastructure.storage.S3StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/medical-records/{recordId}/documents")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MedicalRecordDocumentController {

    private final MedicalRecordDocumentService service;
    private final S3StorageService s3StorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalRecordDocumentResponse> upload(
            @PathVariable UUID recordId,
            @RequestParam UUID profileId,
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false) String description) {

        try {
            MedicalRecordDocumentResponse response = service.upload(recordId, profileId, file, description);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(response.id())
                    .toUri();

            return ResponseEntity.created(location).body(response);

        } catch (IllegalArgumentException | BusinessException ex) {
            log.warn("Upload inválido para recordId {} perfil {}: {}", recordId, profileId, ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (EntityNotFoundException ex) {
            log.warn("Recurso não encontrado ao tentar upload: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Erro inesperado no upload para recordId {}: {}", recordId, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar upload", ex);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalRecordDocumentResponse>> list(@PathVariable UUID recordId) {
        try {
            return ResponseEntity.ok(service.listByRecord(recordId));
        } catch (EntityNotFoundException ex) {
            log.warn("Lista não encontrada para recordId {}: {}", recordId, ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Erro ao listar documentos para recordId {}: {}", recordId, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar documentos", ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            log.warn("Tentativa de deletar documento inexistente id {}: {}", id, ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (BusinessException ex) {
            log.warn("Erro de negócio ao deletar documento id {}: {}", id, ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Erro inesperado ao deletar documento id {}: {}", id, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar documento", ex);
        }
    }

    /**
     * Gera uma URL presignada para download a partir de uma *key* válida.
     * Nota: por segurança recomenda-se expor apenas através de ID do documento via serviço de aplicação.
     */
    @GetMapping(value = "/download-url", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateDownloadUrl(@PathVariable UUID recordId,
                                                      @RequestParam("key") String key) {
        if (!StringUtils.hasText(key)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chave de storage inválida");
        }

        // pequena sanitização básica: evitar caracteres de nova linha e espaços extremos
        String sanitized = key.trim();
        if (sanitized.contains("\n") || sanitized.contains("\r")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chave de storage inválida");
        }

        try {
            String url = s3StorageService.generateDownloadUrl(sanitized);
            return ResponseEntity.ok(url);
        } catch (BusinessException ex) {
            log.warn("Falha ao gerar URL presignada para key {}: {}", sanitized, ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Erro ao gerar URL presignada para key {}: {}", sanitized, ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar URL de download", ex);
        }
    }
}