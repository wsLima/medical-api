package br.com.wsystechnologies.medical.application.dto.medicalRecord;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MedicalRecordResponse extends BaseDTO {
    private ClinicResponse clinic;
    private PatientResponse patient;

    private ProfessionalResponse createdBy;

    private String mainIssue;
    private Map<String, Object> anamnesis;
    private String notes;
}

