package br.com.wsystechnologies.medical.application.dto.doctorAvailableDay;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DoctorAvailableDayRequest {
    /**
     * id do doctor (professional.id)
     */
    private UUID doctorId;

    /**
     * dia da semana 1..7
     */
    private Integer day;
}