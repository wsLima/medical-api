package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.DocumentType;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private Profile uploadedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String storagePath;

    private String mimeType;

    private Long sizeBytes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
    }

}