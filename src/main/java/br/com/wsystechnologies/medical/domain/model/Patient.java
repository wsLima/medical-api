package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends BaseEntity {



    private String fullName;
    private LocalDate dateOfBirth;
    private String documentId;
    private String phone;
    private String email;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Profile createdBy;

    @Builder.Default
    private boolean active = true;
}


