package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.application.mapper.ClinicMapper;
import br.com.wsystechnologies.medical.domain.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper mapper;


}
