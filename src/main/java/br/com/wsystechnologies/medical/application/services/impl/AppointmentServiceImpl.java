package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentRequest;
import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.application.mapper.AppointmentMapper;
import br.com.wsystechnologies.medical.application.services.AppointmentService;
import br.com.wsystechnologies.medical.domain.enums.AppointmentStatus;
import br.com.wsystechnologies.medical.domain.model.*;
import br.com.wsystechnologies.medical.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final ProfileRepository profileRepository;
    private final ServiceProvidedRepository serviceRepository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentResponse create(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new EntityNotFoundException("Clinic not found"));

        Professional doctor = doctorRepository.findById(request.getProfessionalId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        ServiceProvided serviceProvided = serviceRepository.findById(request.getServiceProvidedId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Appointment entity = mapper.toEntity(request);
        entity.setClinic(clinic);
        entity.setPatient(patient);
        entity.setProfessional(doctor);
        entity.setStatus(AppointmentStatus.SCHEDULED);
        entity.setServiceProvided(serviceProvided);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public AppointmentResponse update(UUID id, AppointmentRequest request) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Professional doctor = doctorRepository.findById(request.getProfessionalId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        ServiceProvided serviceProvided = serviceRepository.findById(request.getServiceProvidedId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        mapper.updateEntityFromRequest(request, existing);
        existing.setPatient(patient);
        existing.setProfessional(doctor);
        existing.setServiceProvided(serviceProvided);

        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Appointment not found");

        repository.deleteById(id);
    }

    @Override
    public AppointmentResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
    }

    @Override
    public List<AppointmentResponse> findAllByPatient(UUID patientId) {
        return repository.findAllByPatientId(patientId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> findAllByDoctor(UUID doctorId) {
        return repository.findAllByProfessionalId(doctorId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> findAllByProfessional(UUID professionalId) {
        return repository.findAllByProfessionalId(professionalId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> findAllByClinic(UUID clinicId) {
        return repository.findAllByClinicId(clinicId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public AppointmentResponse checkIn(UUID appointmentId, UUID userId) {
        Appointment appt = repository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Profile actor = profileRepository.findByAccountId(userId)
                .orElseThrow(() ->  new EntityNotFoundException("Profile not found"));
        appt.setCheckinAt(Instant.now());
        appt.setCheckedInBy(actor);
        appt.setStatus(AppointmentStatus.CONFIRMED);

        Appointment saved = repository.save(appt);
        return mapper.toResponse(saved);
    }

    @Override
    public AppointmentResponse checkOut(UUID appointmentId, UUID userId) {
        Appointment appt = repository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        Profile actor = profileRepository.findByAccountId(userId)
                .orElseThrow(() ->  new EntityNotFoundException("Profile not found"));
                appt.setCheckoutAt(Instant.now());
        appt.setCheckedOutBy(actor);
        appt.setStatus(AppointmentStatus.COMPLETED);

        Appointment saved = repository.save(appt);
        return mapper.toResponse(saved);
    }

}
