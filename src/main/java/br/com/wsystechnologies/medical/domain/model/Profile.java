package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
