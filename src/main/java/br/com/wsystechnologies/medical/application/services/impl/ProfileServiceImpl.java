package br.com.wsystechnologies.medical.application.services.impl;


import br.com.wsystechnologies.medical.application.dto.profile.ProfileRequest;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import br.com.wsystechnologies.medical.application.mapper.ProfileMapper;
import br.com.wsystechnologies.medical.application.services.ProfileService;
import br.com.wsystechnologies.medical.domain.model.Profile;
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
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    @Override
    public ProfileResponse create(ProfileRequest request) {
        Profile entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public ProfileResponse update(UUID id, ProfileRequest request) {
        Profile entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Profile not found: " + id);

        repository.deleteById(id);
    }

    @Override
    public ProfileResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found: " + id));
    }

    @Override
    public List<ProfileResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProfileResponse> findAllByRole(String role) {
        return repository.findAllByRole(role)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProfileResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
