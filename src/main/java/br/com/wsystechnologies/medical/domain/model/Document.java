package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.DocumentType;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Document extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false, length = 180)
    private String name;

    @Column(length = 500)
    private String description;

    /**
     * Caminho para o arquivo (S3, MinIO, FileSystem, etc.)
     */
    @Column(nullable = false)
    private String storagePath;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Profile createdBy;

}