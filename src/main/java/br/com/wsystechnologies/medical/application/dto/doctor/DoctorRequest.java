package br.com.wsystechnologies.medical.application.dto.doctor;

import br.com.wsystechnologies.medical.domain.enums.Specialty;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record DoctorRequest(

    @NotNull(message = "usuário é obrigatório")
    UUID userId,

    @NotBlank(message = "O CRM é obrigatório")
    @Size(min = 4, max = 20)
    String crm,

    @NotNull(message = "A especialidade é obrigatória")
    Specialty specialty,

    @NotEmpty(message = "Os dias disponíveis não podem ser vazios")
    List<@Min(1) @Max(7) Integer> availableDays,

    @NotNull(message = "Horário inicial é obrigatório")
    LocalTime availableFrom,

    @NotNull(message = "Horário final é obrigatório")
    LocalTime availableTo,

    @NotNull(message = "Status ativo é obrigatório")
    Boolean active
) {}
