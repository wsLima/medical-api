package br.com.wsystechnologies.medical.application.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
}