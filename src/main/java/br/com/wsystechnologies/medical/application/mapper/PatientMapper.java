package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.patient.PatientRequest;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.domain.model.Patient;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class, PersonMapper.class, ClinicMapper.class }
)
public interface PatientMapper {

    PatientResponse toResponse(Patient entity);

    Patient toEntity(PatientRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(PatientRequest request, @MappingTarget Patient entity);
}