package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionRequest;
import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionResponse;
import br.com.wsystechnologies.medical.domain.model.Prescription;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionResponse toResponse(Prescription entity);

    Prescription toEntity(PrescriptionRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(PrescriptionRequest request, @MappingTarget Prescription entity);
}