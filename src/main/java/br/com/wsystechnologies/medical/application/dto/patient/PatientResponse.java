package br.com.wsystechnologies.medical.application.dto.patient;

import br.com.wsystechnologies.medical.application.dto.address.AddressResponse;
import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PatientResponse extends BaseDTO {
    private ClinicResponse clinic;
    private String fullName;
    private LocalDate dateOfBirth;
    private String documentId;
    private String phone;
    private String email;

    private AddressResponse address;
    private String notes;
    private Boolean active;

    private UUID createdBy;
}
