package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingRequest;
import br.com.wsystechnologies.medical.application.dto.clinicSetting.ClinicSettingResponse;

import java.util.List;
import java.util.UUID;

public interface ClinicSettingService {

    ClinicSettingResponse create(ClinicSettingRequest request);

    ClinicSettingResponse update(UUID id, ClinicSettingRequest request);

    ClinicSettingResponse findById(UUID id);

    ClinicSettingResponse findByClinicAndKey(UUID clinicId, String key);

    List<ClinicSettingResponse> findAllByClinic(UUID clinicId);

    void delete(UUID id);
}

