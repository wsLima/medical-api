package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentRequest;
import br.com.wsystechnologies.medical.application.dto.appointment.AppointmentResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentResponse create(AppointmentRequest request);

    AppointmentResponse update(UUID id, AppointmentRequest request);

    void delete(UUID id);

    AppointmentResponse findById(UUID id);

    List<AppointmentResponse> findAllByPatient(UUID patientId);

    List<AppointmentResponse> findAllByDoctor(UUID doctorId);

    List<AppointmentResponse> findAllByProfessional(UUID professionalId);

    List<AppointmentResponse> findAllByClinic(UUID clinicId);

    List<AppointmentResponse> findAll();

    AppointmentResponse checkIn(UUID appointmentId, UUID userId);

    AppointmentResponse checkOut(UUID appointmentId, UUID userId);
}