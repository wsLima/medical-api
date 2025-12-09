package br.com.wsystechnologies.medical.domain.repository;

import br.com.wsystechnologies.medical.domain.model.DoctorAvailableDay;
import br.com.wsystechnologies.medical.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorAvailableDayRepository extends JpaRepository<DoctorAvailableDay, UUID> {

    // Verificar se já existe um registro para um médico em um dia específico
    boolean existsByDoctorAndDay(Professional doctor, Integer day);

    List<DoctorAvailableDay> findAllByDoctorId(UUID doctorId);
}