package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.ClinicSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicSettingRepository extends JpaRepository<ClinicSetting, UUID> {

    Optional<ClinicSetting> findByClinicIdAndKey(UUID clinicId, String key);

    List<ClinicSetting> findAllByClinicId(UUID clinicId);
}
