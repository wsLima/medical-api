package br.com.wsystechnologies.medical.application.dto.medicalRecord;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class MedicalRecordRequest {
    private UUID clinicId;
    private UUID patientId;
    private UUID createdById;
    private String mainIssue;
    private Map<String, Object> anamnesis;
    private String notes;
}
