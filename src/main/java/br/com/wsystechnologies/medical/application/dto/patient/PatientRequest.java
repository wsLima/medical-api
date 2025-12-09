package br.com.wsystechnologies.medical.application.dto.patient;

import br.com.wsystechnologies.medical.application.dto.address.AddressRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.time.LocalDate;

@Getter
@Setter
public class PatientRequest {
    private UUID clinicId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String documentId;
    private String phone;
    private String email;
    private AddressRequest address;
    private String notes;
}