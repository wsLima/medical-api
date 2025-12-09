package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByPatientId(UUID patientId);

    List<Appointment> findAllByProfessionalId(UUID professionalId);

    List<Appointment> findAllByClinicId(UUID clinicId);

}
