package br.com.wsystechnologies.medical.application.dto.evolution;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EvolutionResponse extends BaseDTO {
    private ClinicResponse clinic;
    private PatientResponse patient;
    private ProfessionalResponse professional;
    private AppointmentResponse appointment;

    private String notes;
    private Map<String, Object> structuredData;
}
