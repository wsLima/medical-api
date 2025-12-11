package br.com.wsystechnologies.medical.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/auth/**",
            "/api/v3/api-docs/**",
            "/api/swagger-ui/**",
            "/api/swagger-ui.html"
    };

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        configureCsrf(http);
        configureCors(http);
        configureSession(http);
        configureExceptionHandling(http);
        configureAuthorization(http);
        configureJwtFilter(http);

        SecurityFilterChain chain = http.build();
        return chain;
    }

    private void configureAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(AUTH_WHITELIST).permitAll();
            auth.anyRequest().authenticated();
        });

        http.authenticationProvider(authenticationProvider);
    }

    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(csrf -> {
            csrf.disable();
        });
    }

    private void configureCors(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
    }

    private void configureSession(HttpSecurity http) throws Exception {
        http.sessionManagement(sm -> {
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
    }

    private void configureExceptionHandling(HttpSecurity http) throws Exception {
        http.exceptionHandling(ex -> {
            ex.authenticationEntryPoint(authEntryPoint);
        });
    }

    private void configureJwtFilter(HttpSecurity http) {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
