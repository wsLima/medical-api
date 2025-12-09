package br.com.wsystechnologies.medical.application.dto.document;

import br.com.wsystechnologies.medical.domain.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DocumentRequest {
    private UUID clinicId;
    private UUID patientId;
    private UUID appointmentId;
    private DocumentType type;
    private String name;
    private String description;
    private String storagePath;
    private UUID createdById;
}
