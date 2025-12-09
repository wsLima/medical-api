package br.com.wsystechnologies.medical.application.services;


import br.com.wsystechnologies.medical.application.dto.account.AccountRequest;
import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse create(AccountRequest request);

    AccountResponse update(UUID id, AccountRequest request);

    void delete(UUID id);

    AccountResponse findById(UUID id);

    List<AccountResponse> findAllByClinic(UUID clinicId);
}
