package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalRequest;
import br.com.wsystechnologies.medical.application.dto.professional.ProfessionalResponse;
import br.com.wsystechnologies.medical.domain.model.Professional;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {PersonMapper.class, ClinicMapper.class}
)
public interface ProfessionalMapper {

    ProfessionalResponse toResponse(Professional entity);

    Professional toEntity(ProfessionalRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ProfessionalRequest request, @MappingTarget Professional entity);
}