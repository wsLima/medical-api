package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.doctorAvailableDay.DoctorAvailableDayRequest;
import br.com.wsystechnologies.medical.application.dto.doctorAvailableDay.DoctorAvailableDayResponse;
import br.com.wsystechnologies.medical.domain.model.DoctorAvailableDay;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface DoctorAvailableDayMapper {

    DoctorAvailableDayResponse toResponse(DoctorAvailableDay entity);

    DoctorAvailableDay toEntity(DoctorAvailableDayRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(DoctorAvailableDayRequest request, @MappingTarget DoctorAvailableDay entity);
}