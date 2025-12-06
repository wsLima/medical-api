package br.com.wsystechnologies.medical.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {

    private String street;
    private String number;
    private String complement;

    private String addressNeighborhood;
    private String city;
    private String state;

    private String zipCode;
}