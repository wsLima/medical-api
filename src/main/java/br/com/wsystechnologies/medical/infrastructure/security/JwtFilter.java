package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.model.Profile;
import br.com.wsystechnologies.medical.domain.model.User;
import br.com.wsystechnologies.medical.domain.repository.ProfileRepository;
import br.com.wsystechnologies.medical.domain.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();

        UUID userId;
        try {
            Claims claims = jwtProvider.validateToken(token);
            userId = UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                User user = userRepository.findById(userId).orElseThrow();
                Profile profile = profileRepository.findById(userId).orElseThrow();

                UserPrincipal authUser = new UserPrincipal(user, profile);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                authUser,
                                null,
                                authUser.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ignored) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
