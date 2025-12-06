package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.application.dto.doctor.DoctorRequest;
import br.com.wsystechnologies.medical.application.dto.doctor.DoctorResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Professional toEntity(DoctorRequest dto) {
        return Professional.builder()
                .crm(dto.crm())
                .specialty(dto.specialty())
                .availableDays(dto.availableDays())
                .availableFrom(dto.availableFrom())
                .availableTo(dto.availableTo())
                .active(dto.active())
                .build();
    }

    public Professional toEntity(Professional entity, DoctorRequest dto) {
        entity.setCrm(dto.crm());
        entity.setSpecialty(dto.specialty());
        entity.setAvailableDays(dto.availableDays());
        entity.setAvailableFrom(dto.availableFrom());
        entity.setAvailableTo(dto.availableTo());
        entity.setActive(dto.active());
        return entity;
    }

    public DoctorResponse toDTO(Professional professional) {
        return new DoctorResponse(
                professional.getId(),
//                professional.getUser().getId(),
//                professional.getUser().getName(),
                professional.getCrm(),
                professional.getSpecialty(),
                professional.getAvailableDays(),
                professional.getAvailableFrom(),
                professional.getAvailableTo(),
                professional.getActive()
        );
    }

}
