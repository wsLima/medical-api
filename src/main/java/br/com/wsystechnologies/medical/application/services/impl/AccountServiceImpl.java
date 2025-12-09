package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.account.AccountRequest;
import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;
import br.com.wsystechnologies.medical.application.mapper.AccountMapper;
import br.com.wsystechnologies.medical.application.services.AccountService;
import br.com.wsystechnologies.medical.domain.model.Account;
import br.com.wsystechnologies.medical.domain.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountResponse create(AccountRequest request) {
        Account entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public AccountResponse update(UUID id, AccountRequest request) {
        Account entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Account not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public AccountResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
    }

    @Override
    public List<AccountResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
