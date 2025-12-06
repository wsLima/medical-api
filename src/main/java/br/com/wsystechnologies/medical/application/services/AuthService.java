package br.com.wsystechnologies.medical.application.services;

import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.infrastructure.security.JwtProvider;
import br.com.wsystechnologies.medical.infrastructure.security.UserPrincipal;
import br.com.wsystechnologies.medical.domain.model.User;
import br.com.wsystechnologies.medical.domain.repository.UserRepository;
import br.com.wsystechnologies.medical.application.dto.login.AuthResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * LOGIN
     */
    public AuthResult login(String email, String rawPassword) {

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, rawPassword)
            );

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            return new AuthResult(
                    jwtProvider.generateToken(principal),
                    jwtProvider.generateRefreshToken(principal),
                    principal.getUserId(),
                    principal.getUsername(),
                    principal.getRole(),
                    principal.getEmail()
            );

        } catch (AuthenticationException e) {
            // log reduzido e direto
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    /**
     * REGISTER
     */
    public AuthResult register(String name, String email, String rawPassword, String roleStr) {

//        if (userRepository.existsByEmail(email)) {
//            throw new RuntimeException("Email already in use");
//        }
//
//        Role role = Role.valueOf(roleStr.toUpperCase());
//
//        User user = User.builder()
//                .email(email)
//                .password(passwordEncoder.encode(rawPassword))
//                .enabled(true)
//                .build();
//
//        userRepository.save(user);
//
//        UserPrincipal principal = UserPrincipal.fromDomain(user);
//
//        return new AuthResult(
//                jwtProvider.generateAccessToken(principal),
//                jwtProvider.generateRefreshToken(principal),
//                user.getId(),
//                user.getName(),
//                user.getRole().name(),
//                user.getEmail()
//        );
        return null;
    }

    /**
     * REFRESH
     */
    public AuthResult refresh(String refreshToken) {

//        String email = jwtProvider.extractUsername(refreshToken);
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        UserPrincipal principal = UserPrincipal.fromDomain(user);
//
//        if (!jwtProvider.isRefreshTokenValid(refreshToken, principal)) {
//            throw new RuntimeException("Invalid refresh token");
//        }
//
//        return new AuthResult(
//                jwtProvider.generateAccessToken(principal),
//                jwtProvider.generateRefreshToken(principal),
//                user.getId(),
//                user.getName(),
//                user.getRole().name(),
//                user.getEmail()
//        );
        return  null;
    }
}

