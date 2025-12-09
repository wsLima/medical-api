package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.patient.PatientRequest;
import br.com.wsystechnologies.medical.application.dto.patient.PatientResponse;
import br.com.wsystechnologies.medical.application.mapper.AddressMapper;
import br.com.wsystechnologies.medical.application.mapper.PatientMapper;
import br.com.wsystechnologies.medical.application.services.PatientService;
import br.com.wsystechnologies.medical.common.SecurityUtils;
import br.com.wsystechnologies.medical.domain.model.Address;
import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.domain.model.Patient;
import br.com.wsystechnologies.medical.domain.model.Profile;
import br.com.wsystechnologies.medical.domain.repository.AddressRepository;
import br.com.wsystechnologies.medical.domain.repository.ClinicRepository;
import br.com.wsystechnologies.medical.domain.repository.PatientRepository;
import br.com.wsystechnologies.medical.domain.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final ClinicRepository clinicRepository;
    private final AddressRepository addressRepository;
    private final PatientRepository repository;
    private final ProfileRepository profileRepository;

    private final PatientMapper mapper;
    private final AddressMapper addressMapper;

    @Override
    public PatientResponse create(PatientRequest request) {

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        Address address = addressMapper.toEntity(request.getAddress());
        addressRepository.save(address);

        Profile createdBy = profileRepository
                .findById(SecurityUtils.getCurrentProfileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        Patient entity = mapper.toEntity(request);

        entity.setClinic(clinic);
        entity.setAddress(address);
        entity.setCreatedBy(createdBy);
        entity.setActive(true);

        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public PatientResponse update(UUID id, PatientRequest request) {
        Patient entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Patient not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public PatientResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    @Override
    public List<PatientResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PatientResponse> findAllByName(String name) {
        return repository.findByFullNameContainingIgnoreCase(name).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<PatientResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
