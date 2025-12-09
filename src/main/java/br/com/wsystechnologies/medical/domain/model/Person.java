package br.com.wsystechnologies.medical.domain.model;

import br.com.wsystechnologies.medical.domain.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Person extends BaseEntity {

    private String fullName;

    @Column(unique = true)
    private String cpf;

    private String rg;

    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Builder.Default
    private boolean active = true;
}