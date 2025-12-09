package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogRequest;
import br.com.wsystechnologies.medical.application.dto.auditLog.AuditLogResponse;
import br.com.wsystechnologies.medical.domain.model.AuditLog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    @Mapping(source = "details", target = "details", qualifiedByName = "stringToMap")
    AuditLogResponse toResponse(AuditLog entity);

    @Mapping(source = "details", target = "details", qualifiedByName = "mapToString")
    AuditLog toEntity(AuditLogRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "details", target = "details", qualifiedByName = "mapToString")
    void updateEntityFromRequest(AuditLogRequest request, @MappingTarget AuditLog entity);

    // String -> Map
    @Named("stringToMap")
    default Map<String, Object> stringToMap(String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            return new ObjectMapper().readValue(value, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON: " + value, e);
        }
    }

    // Map -> String
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