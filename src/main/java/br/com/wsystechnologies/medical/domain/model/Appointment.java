package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.AppointmentStatus;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @Column(nullable = false)
    private OffsetDateTime startsAt;

    @Column(nullable = false)
    private OffsetDateTime endsAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Profile createdBy;

}
