package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.clinic.ClinicRequest;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class}
)
public interface ClinicMapper {

    ClinicResponse toResponse(Clinic entity);

    Clinic toEntity(ClinicRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ClinicRequest request, @MappingTarget Clinic entity);
}
