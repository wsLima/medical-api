package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile user;

    @Column(nullable = false)
    private String entityType;

    private UUID entityId;

    @Column(nullable = false)
    private String action;

    @Column(columnDefinition = "jsonb")
    private String details;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
    }

}

