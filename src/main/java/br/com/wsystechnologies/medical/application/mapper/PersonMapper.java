package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.person.PersonRequest;
import br.com.wsystechnologies.medical.application.dto.person.PersonResponse;
import br.com.wsystechnologies.medical.domain.model.Person;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse toResponse(Person entity);

    Person toEntity(PersonRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(PersonRequest request, @MappingTarget Person entity);
}