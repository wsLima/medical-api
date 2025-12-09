package br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay;

import br.com.wsystechnologies.medical.application.dto.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DoctorAvailableDayResponse extends BaseDTO {
    /**
     * id do profissional (professional.id)
     */
    private UUID doctorId;

    /**
     * dia da semana (1..7)
     */
    private Integer day;
}