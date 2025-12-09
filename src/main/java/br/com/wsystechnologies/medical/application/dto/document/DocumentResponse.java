package br.com.wsystechnologies.medical.application.dto.document;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.domain.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponse extends BaseDTO {
    private ClinicResponse clinic;
    private PatientResponse patient;
    private AppointmentResponse appointment;

    private DocumentType type;
    private String name;
    private String description;
    private String storagePath;

    private ProfileResponse createdBy;
}
