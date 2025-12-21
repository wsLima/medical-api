package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionRequest;
import br.com.wsystechnologies.medical.application.dto.prescription.PrescriptionResponse;
import br.com.wsystechnologies.medical.application.mapper.PrescriptionMapper;
import br.com.wsystechnologies.medical.application.services.PrescriptionService;
import br.com.wsystechnologies.medical.domain.model.Prescription;
import br.com.wsystechnologies.medical.domain.repository.PrescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final PrescriptionMapper mapper;

    @Override
    public PrescriptionResponse create(PrescriptionRequest request) {
        Prescription entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public PrescriptionResponse update(UUID id, PrescriptionRequest request) {
        Prescription existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prescription not found"));

        mapper.updateEntityFromRequest(request, existing);
        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Prescription not found");

        repository.deleteById(id);
    }

    @Override
    public PrescriptionResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Prescription not found"));
    }

    @Override
    public List<PrescriptionResponse> findAllByPatient(UUID patientId) {
        return repository.findAllByPatientId(patientId).stream()
                .map(mapper::toResponse).toList();
    }

    @Override
    public List<PrescriptionResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId).stream()
                .map(mapper::toResponse).toList();
    }

    @Override
    public List<PrescriptionResponse> findAllByProfessional(UUID professionalId) {
        return repository.findAllByProfessionalId(professionalId).stream()
                .map(mapper::toResponse).toList();
    }

    @Override
    public List<PrescriptionResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse).toList();
    }
}
