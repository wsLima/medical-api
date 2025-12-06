package br.com.wsystechnologies.medical.application.dto.doctor;

import br.com.wsystechnologies.medical.domain.enums.Specialty;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record DoctorResponse (
    UUID id,
//    UUID userId,
//    String name,
    String crm,
    Specialty specialty,
    List<Integer> availableDays,
    LocalTime availableFrom,
    LocalTime availableTo,
    Boolean active
) {}
