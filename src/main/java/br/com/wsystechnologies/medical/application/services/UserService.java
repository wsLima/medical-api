package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.domain.model.User;
import br.com.wsystechnologies.medical.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
