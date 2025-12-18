package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingRequest;
import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingResponse;
import br.com.wsystechnologies.medical.domain.model.ClinicSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicSettingMapper {

    @Mapping(source = "clinic.id", target = "clinicId")
    ClinicSettingResponse toResponse(ClinicSetting entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clinic", ignore = true)
    ClinicSetting toEntity(ClinicSettingRequest request);
}
