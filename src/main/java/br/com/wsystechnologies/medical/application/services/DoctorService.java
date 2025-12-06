package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.exceptions.BusinessException;
import br.com.wsystechnologies.medical.exceptions.NotFoundException;
import br.com.wsystechnologies.medical.domain.model.Professional;
import br.com.wsystechnologies.medical.domain.model.User;
import br.com.wsystechnologies.medical.domain.repository.DoctorRepository;
import br.com.wsystechnologies.medical.domain.repository.UserRepository;
import br.com.wsystechnologies.medical.application.dto.doctor.DoctorRequest;
import br.com.wsystechnologies.medical.application.mapper.DoctorMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final UserRepository userRepository;
    private final DoctorMapper mapper;

    @Transactional
    public Professional create(DoctorRequest dto) {

        validateUser(dto.userId());
        validateCRM(dto.crm());
        validateSchedule(dto.availableFrom(), dto.availableTo());
        validateAvailableDays(dto.availableDays());

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Professional professional = mapper.toEntity(dto);
//        professional.setUser(user);

        return repository.save(professional);
    }

    @Transactional
    public Professional update(UUID id, DoctorRequest dto) {

        Professional professional = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));

        if (!professional.getCrm().equals(dto.crm())) {
            validateCRM(dto.crm());
        }

        validateSchedule(dto.availableFrom(), dto.availableTo());
        validateAvailableDays(dto.availableDays());

        mapper.toEntity(professional, dto);
        return repository.save(professional);
    }

    public Collection<Professional> findAll() {
        return repository.findAll();
    }

    public Professional findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Médico não encontrado");
        }
        repository.deleteById(id);
    }

    private void validateUser(UUID userId) {
        if (userId == null) {
            throw new BusinessException("User ID é obrigatório");
        }

        // Apenas demonstração — depois conecte no seu Auth Service
        boolean userExists = true;

        if (!userExists) {
            throw new BusinessException("Usuário informado não existe");
        }
    }

    private void validateCRM(String crm) {
        boolean exists = repository.existsByCrmIgnoreCase(crm);
        if (exists) {
            throw new BusinessException("Já existe um médico cadastrado com o CRM informado");
        }
    }

    private void validateSchedule(LocalTime from, LocalTime to) {
        if (from.isAfter(to)) {
            throw new BusinessException("O horário de início não pode ser maior que o horário final");
        }

        if (from.equals(to)) {
            throw new BusinessException("O horário de atendimento deve ter pelo menos 1 minuto");
        }
    }

    private void validateAvailableDays(java.util.List<Integer> days) {
        if (days == null || days.isEmpty()) {
            throw new BusinessException("O médico deve ter ao menos um dia de atendimento");
        }

        boolean invalid = days.stream().anyMatch(d -> d < 1 || d > 7);

        if (invalid) {
            throw new BusinessException("Dias disponíveis devem estar entre 1 e 7");
        }
    }


}
