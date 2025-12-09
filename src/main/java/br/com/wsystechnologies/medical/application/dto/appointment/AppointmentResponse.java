package br.com.wsystechnologies.medical.application.dto.appointment;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.domain.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AppointmentResponse extends BaseDTO {
    private ClinicResponse clinic;
    private PatientResponse patient;
    private ProfessionalResponse professional;

    private Instant startsAt;
    private Instant endsAt;
    private AppointmentStatus status;
    private String notes;

    private ProfileResponse createdBy;
}

