package br.com.wsystechnologies.medical.infrastructure.storage;

import br.com.wsystechnologies.medical.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.medical-prefix}")
    private String prefix;

    @Value("${aws.s3.presign-minutes}")
    private long presignMinutes;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    private static final String DEFAULT_MIME = "application/octet-stream";
    private static final int MAX_FILENAME_LENGTH = 255;

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Arquivo não informado ou vazio");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        if (original.isBlank()) {
            original = "file";
        }

        if (original.length() > MAX_FILENAME_LENGTH) {
            original = original.substring(original.length() - MAX_FILENAME_LENGTH);
        }

        if (original.contains("..")) {
            throw new BusinessException("Nome de arquivo inválido");
        }

        String key = prefix + "/" + UUID.randomUUID() + "-" + original;
        String contentType = file.getContentType() == null ? DEFAULT_MIME : file.getContentType();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
            return key;
        } catch (IOException e) {
            log.error("Falha ao ler bytes do arquivo para upload", e);
            throw new BusinessException("Erro ao processar arquivo para upload");
        } catch (SdkException e) {
            log.error("Erro S3 ao enviar objeto: {}", key, e);
            throw new BusinessException("Erro ao enviar arquivo para storage");
        }
    }

    public void delete(String key) {
        if (key == null || key.isBlank()) {
            throw new BusinessException("Chave inválida para remoção");
        }

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(request);
        } catch (SdkException e) {
            log.error("Erro ao deletar objeto do S3: {}", key, e);
            throw new BusinessException("Erro ao remover arquivo do storage");
        }
    }

    public String generateDownloadUrl(String key) {
        if (key == null || key.isBlank()) {
            throw new BusinessException("Chave inválida para geração de URL");
        }

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(presignMinutes))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (SdkException e) {
            log.error("Erro ao gerar URL presign para a chave: {}", key, e);
            throw new BusinessException("Erro ao gerar URL de download");
        }
    }

}