package br.com.wsystechnologies.medical.application.mapper;


import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentRequest;
import br.com.wsystechnologies.medical.application.dto.medicalRecordDocuments.MedicalRecordDocumentResponse;
import br.com.wsystechnologies.medical.domain.model.MedicalRecordDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicalRecordDocumentMapper {

    MedicalRecordDocumentResponse toResponse(MedicalRecordDocument entity);

    MedicalRecordDocument toEntity(MedicalRecordDocumentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(MedicalRecordDocumentRequest request, @MappingTarget MedicalRecordDocument entity);
}
