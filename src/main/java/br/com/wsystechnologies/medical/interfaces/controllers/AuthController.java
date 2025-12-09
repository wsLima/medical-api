package br.com.wsystechnologies.medical.interfaces.controllers;

import br.com.wsystechnologies.medical.application.dto.login.*;
import br.com.wsystechnologies.medical.domain.model.Profile;
import br.com.wsystechnologies.medical.domain.repository.ProfileRepository;
import br.com.wsystechnologies.medical.infrastructure.security.JwtProvider;
import br.com.wsystechnologies.medical.domain.model.RefreshToken;
import br.com.wsystechnologies.medical.infrastructure.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final ProfileRepository profileRepository;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
//        Profile profile = profileRepository.findById(user.getUserId()).orElseThrow();

        String accessToken = jwtProvider.generateToken(user);
        RefreshToken refreshToken = jwtProvider.createRefreshToken(user.getAccountId());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest request) {
        return jwtProvider.validateRefreshToken(request.refreshToken())
                .map(rt -> {
                    Profile profile = profileRepository.findById(rt.getAccountId()).orElseThrow();
                    String newAccessToken = jwtProvider.generateToken(rt.getAccountId(), profile.getClinic().getId(), profile.getRole());
                    return ResponseEntity.ok(new LoginResponse(newAccessToken, rt.getToken()));
                })
                .orElseThrow(() -> new RuntimeException("Invalid or expired refresh token"));
    }
}
