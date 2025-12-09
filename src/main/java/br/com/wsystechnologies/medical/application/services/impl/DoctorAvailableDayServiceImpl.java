package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayRequest;
import br.com.wsystechnologies.medical.application.dto.DoctorAvailableDay.DoctorAvailableDayResponse;
import br.com.wsystechnologies.medical.application.mapper.DoctorAvailableDayMapper;
import br.com.wsystechnologies.medical.application.services.DoctorAvailableDayService;
import br.com.wsystechnologies.medical.domain.model.DoctorAvailableDay;
import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.domain.repository.DoctorAvailableDayRepository;
import br.com.wsystechnologies.medical.domain.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorAvailableDayServiceImpl implements DoctorAvailableDayService {

    private final DoctorAvailableDayRepository repository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailableDayMapper mapper;

    @Override
    public DoctorAvailableDayResponse create(DoctorAvailableDayRequest request) {
        Professional doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        DoctorAvailableDay entity = mapper.toEntity(request);
        entity.setDoctor(doctor);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public DoctorAvailableDayResponse update(UUID id, DoctorAvailableDayRequest request) {
        DoctorAvailableDay existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DoctorAvailableDay not found"));

        Professional doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        mapper.updateEntityFromRequest(request, existing);
        existing.setDoctor(doctor);

        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("DoctorAvailableDay not found");

        repository.deleteById(id);
    }

    @Override
    public DoctorAvailableDayResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("DoctorAvailableDay not found"));
    }

    @Override
    public List<DoctorAvailableDayResponse> findAllByDoctor(UUID doctorId) {
        return repository.findAllByDoctorId(doctorId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<DoctorAvailableDayResponse> findAllByDoctorId(UUID doctorId) {
        return repository.findAllByDoctorId(doctorId).stream().map( mapper::toResponse).toList();
    }
}

