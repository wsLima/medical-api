package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.Specialty;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Table(name = "professionals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Professional extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(unique = true)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    @Builder.Default
    private boolean active = true;
}
