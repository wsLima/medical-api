package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.document.DocumentRequest;
import br.com.wsystechnologies.medical.application.dto.document.DocumentResponse;
import br.com.wsystechnologies.medical.domain.model.Document;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {PatientMapper.class, ClinicMapper.class}
)
public interface DocumentMapper {

    DocumentResponse toResponse(Document entity);

    Document toEntity(DocumentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(DocumentRequest request, @MappingTarget Document entity);
}
