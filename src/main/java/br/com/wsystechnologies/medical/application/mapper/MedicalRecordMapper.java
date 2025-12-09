package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordRequest;
import br.com.wsystechnologies.medical.application.dto.medicalRecord.MedicalRecordResponse;
import br.com.wsystechnologies.medical.domain.model.MedicalRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.util.Map;

@Mapper(
        componentModel = "spring",
        uses = {PatientMapper.class, ProfessionalMapper.class, ClinicMapper.class}
)
public interface MedicalRecordMapper {

    @Mapping(source = "anamnesis", target = "anamnesis", qualifiedByName = "stringToMap")
    MedicalRecordResponse toResponse(MedicalRecord entity);

    @Mapping(source = "anamnesis", target = "anamnesis", qualifiedByName = "mapToString")
    MedicalRecord toEntity(MedicalRecordRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "anamnesis", target = "anamnesis", qualifiedByName = "mapToString")
    void updateEntityFromRequest(MedicalRecordRequest request, @MappingTarget MedicalRecord entity);

    @Named("stringToMap")
    default Map<String, Object> stringToMap(String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            return new ObjectMapper().readValue(value, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON: " + value, e);
        }
    }

    @Named("mapToString")
    default String mapToString(Map<String, Object> value) {
        if (value == null || value.isEmpty()) return null;
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize Map: " + value, e);
        }
    }
}