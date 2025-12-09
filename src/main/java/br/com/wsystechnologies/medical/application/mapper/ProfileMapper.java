package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.profile.ProfileRequest;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.domain.model.Profile;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { AccountMapper.class }
)
public interface ProfileMapper {

    ProfileResponse toResponse(Profile entity);

    Profile toEntity(ProfileRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(ProfileRequest request, @MappingTarget Profile entity);
}