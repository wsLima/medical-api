package br.com.wsystechnologies.medical.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "doctor_available_days")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DoctorAvailableDay {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // UUID automático
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doctor_id")
    private Professional doctor;

    @Column(nullable = false)
    private Integer day;
}
