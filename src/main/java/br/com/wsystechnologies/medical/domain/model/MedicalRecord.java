package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "medical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Professional createdBy;

    private String mainIssue;

    @Column(columnDefinition = "jsonb")
    private String anamnesis;

    private String notes;
}
