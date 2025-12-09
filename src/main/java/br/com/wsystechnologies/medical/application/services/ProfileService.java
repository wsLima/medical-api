package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.profile.ProfileRequest;
import br.com.wsystechnologies.medical.application.dto.profile.ProfileResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface ProfileService {

    ProfileResponse create(ProfileRequest request);

    ProfileResponse update(UUID id, ProfileRequest request);

    void delete(UUID id);

    ProfileResponse findById(UUID id);

    List<ProfileResponse> findAllByClinic(UUID clinicId);

    List<ProfileResponse> findAllByRole(String role);

    List<ProfileResponse> findAll();
}