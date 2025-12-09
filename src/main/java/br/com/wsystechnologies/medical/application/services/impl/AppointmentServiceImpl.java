package br.com.wsystechnologies.medical.application.services.impl;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentRequest;
import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import br.com.wsystechnologies.medical.application.mapper.AppointmentMapper;
import br.com.wsystechnologies.medical.application.services.AppointmentService;
import br.com.wsystechnologies.medical.domain.model.Appointment;
import br.com.wsystechnologies.medical.domain.model.Patient;
import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.domain.model.ServiceProvided;
import br.com.wsystechnologies.medical.domain.repository.AppointmentRepository;
import br.com.wsystechnologies.medical.domain.repository.DoctorRepository;
import br.com.wsystechnologies.medical.domain.repository.PatientRepository;
import br.com.wsystechnologies.medical.domain.repository.ServiceProvidedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceProvidedRepository serviceRepository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentResponse create(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Professional doctor = doctorRepository.findById(request.getProfessionalId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        ServiceProvided serviceProvided = serviceRepository.findById(request.getServiceProvidedId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        Appointment entity = mapper.toEntity(request);
        entity.setPatient(patient);
        entity.setProfessional(doctor);
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
}
