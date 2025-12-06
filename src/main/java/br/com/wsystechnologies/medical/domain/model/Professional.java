package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.enums.Specialty;
import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "professionals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @Column(nullable = false, unique = true)
    private String crm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialty specialty;

    @ElementCollection
    @CollectionTable(name = "doctor_available_days", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "day")
    private List<Integer> availableDays;
    // ex: Days of week 1=Mon, 7=Sun

    private LocalTime availableFrom;
    private LocalTime availableTo;

    @Column(nullable = false)
    private Boolean active = true;

}
