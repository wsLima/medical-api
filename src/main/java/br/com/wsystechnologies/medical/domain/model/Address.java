package br.com.wsystechnologies.medical.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;

    @Column(length = 2)
    private String state;

    private String zipCode;

    @Builder.Default
    private String type = "RESIDENTIAL";

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;
}