package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayRequest;
import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayResponse;
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