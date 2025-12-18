package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.account.AccountRequest;
import br.com.wsystechnologies.medical.application.dto.account.AccountResponse;
import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingRequest;
import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingResponse;
import br.com.wsystechnologies.medical.application.mapper.AccountMapper;
import br.com.wsystechnologies.medical.application.mapper.ClinicSettingMapper;
import br.com.wsystechnologies.medical.application.services.AccountService;
import br.com.wsystechnologies.medical.application.services.ClinicSettingService;
import br.com.wsystechnologies.medical.domain.model.Account;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.domain.model.ClinicSetting;
import br.com.wsystechnologies.medical.domain.repository.AccountRepository;
import br.com.wsystechnologies.medical.domain.repository.ClinicRepository;
import br.com.wsystechnologies.medical.domain.repository.ClinicSettingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicSettingServiceImpl implements ClinicSettingService {

    private final ClinicSettingRepository repository;
    private final ClinicRepository clinicRepository;
    private final ClinicSettingMapper mapper;

    @Override
    public ClinicSettingResponse create(ClinicSettingRequest request) {

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        // Verifica se já existe a chave para essa clínica
        repository.findByClinicIdAndKey(request.getClinicId(), request.getKey())
                .ifPresent(s -> {
                    throw new IllegalArgumentException("Setting key already exists for this clinic");
                });

        ClinicSetting entity = mapper.toEntity(request);
        entity.setClinic(clinic);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ClinicSettingResponse update(UUID id, ClinicSettingRequest request) {

        ClinicSetting entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));

        // Atualiza campos
        entity.setValue(request.getValue());
        entity.setType(request.getType());
        entity.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ClinicSettingResponse findById(UUID id) {
        ClinicSetting entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));

        return mapper.toResponse(entity);
    }

    @Override
    public ClinicSettingResponse findByClinicAndKey(UUID clinicId, String key) {
        ClinicSetting entity = repository.findByClinicIdAndKey(clinicId, key)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));

        return mapper.toResponse(entity);
    }

    @Override
    public List<ClinicSettingResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        ClinicSetting entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));

        repository.delete(entity);
    }
}

