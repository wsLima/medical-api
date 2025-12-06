package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.domain.model.Clinic;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicRequest;
import br.com.wsystechnologies.medical.application.dto.clinic.ClinicResponse;
import br.com.wsystechnologies.medical.application.mapper.ClinicMapper;
import br.com.wsystechnologies.medical.application.services.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies/{companyId}/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService service;
    private final ClinicMapper mapper;


}
