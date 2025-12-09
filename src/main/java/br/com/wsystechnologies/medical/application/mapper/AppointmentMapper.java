package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentRequest;
import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.domain.model.Appointment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {PatientMapper.class, ProfessionalMapper.class, ClinicMapper.class}
)
public interface AppointmentMapper {

    AppointmentResponse toResponse(Appointment entity);

    Appointment toEntity(AppointmentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AppointmentRequest request, @MappingTarget Appointment entity);
}