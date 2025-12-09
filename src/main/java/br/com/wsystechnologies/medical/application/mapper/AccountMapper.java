package br.com.wsystechnologies.medical.application.mapper;

import br.com.wsystechnologies.medical.application.dto.account.AccountRequest;
import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;
import br.com.wsystechnologies.medical.domain.model.Account;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = { PersonMapper.class }
)
public interface AccountMapper {

    AccountResponse toResponse(Account entity);

    Account toEntity(AccountRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AccountRequest request, @MappingTarget Account entity);
}
