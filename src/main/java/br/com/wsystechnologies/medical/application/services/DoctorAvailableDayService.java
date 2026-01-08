package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.dto.doctorAvailableDay.DoctorAvailableDayRequest;
import br.com.wsystechnologies.medical.application.dto.doctorAvailableDay.DoctorAvailableDayResponse;

import java.util.List;
import java.util.UUID;

public interface DoctorAvailableDayService {

    DoctorAvailableDayResponse create(DoctorAvailableDayRequest request);

    DoctorAvailableDayResponse update(UUID id, DoctorAvailableDayRequest request);

    void delete(UUID id);

    DoctorAvailableDayResponse findById(UUID id);

    List<DoctorAvailableDayResponse> findAllByDoctor(UUID doctorId);

    List<DoctorAvailableDayResponse> findAllByDoctorId(UUID doctorId);
}
