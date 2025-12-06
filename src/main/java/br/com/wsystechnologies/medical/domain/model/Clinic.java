package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "clinics",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_clinic_cnpj", columnNames = "cnpj")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clinic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String legalName;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(length = 20)
    private String phone;

    @Column(length = 120)
    private String email;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
    private List<Professional> professionals;
}