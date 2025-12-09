package br.com.wsystechnologies.medical.application.dto.ServiceProvided;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ServiceProvidedRequest {
    private String name;
    private String description;
    private Double price;
    private Boolean active;
    private UUID clinicId;
}
