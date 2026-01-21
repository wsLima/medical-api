package br.com.wsystechnologies.medical.application.mapper;


import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyRequest;
import br.com.wsystechnologies.medical.application.dto.specialty.SpecialtyResponse;
import br.com.wsystechnologies.medical.domain.model.Specialty;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    SpecialtyResponse toResponse(Specialty entity);

    Specialty toEntity(SpecialtyRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(SpecialtyRequest request, @MappingTarget Specialty entity);
}

