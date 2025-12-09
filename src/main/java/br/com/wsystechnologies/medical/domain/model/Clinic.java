package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "clinics")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Clinic extends BaseEntity {

    private String name;
    private String legalName;

    @Column(unique = true)
    private String cnpj;

    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder.Default
    private boolean active = true;
}