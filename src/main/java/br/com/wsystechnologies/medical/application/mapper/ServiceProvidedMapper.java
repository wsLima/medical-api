package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.ServiceProvided.ServiceProvidedRequest;
import br.com.wsystechnologies.medical.application.dto.ServiceProvided.ServiceProvidedResponse;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.domain.model.ServiceProvided;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServiceProvidedMapper {

    @Mapping(source = "clinic.id", target = "clinicId")
    ServiceProvidedResponse toResponse(ServiceProvided serviceProvided);

    ServiceProvided toEntity(ServiceProvidedRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ServiceProvidedRequest request, @MappingTarget ServiceProvided entity);

    @AfterMapping
    default void setClinic(ServiceProvidedRequest request, @MappingTarget ServiceProvided entity) {
        if (request.getClinicId() != null) {
            entity.setClinic(Clinic.builder().id(request.getClinicId()).build());
        }
    }
}
