package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.model.RefreshToken;
import br.com.wsystechnologies.medical.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private long accessExpirationMs;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Access token: inclui clinicId e role
    public String generateToken(UserPrincipal principal) {
        return Jwts.builder()
                .setSubject(principal.getAccountId().toString())
                .claim("clinicId", principal.getClinicId().toString())
                .claim("role", principal.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Overload opcional caso queira manter chamada com parâmetros
    public String generateToken(UUID userId, UUID clinicId, Enum<?> role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("clinicId", clinicId.toString())
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh token persistido (DB)
    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccountId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(OffsetDateTime.now().plus(Duration.ofMillis(refreshExpirationMs)));
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(OffsetDateTime.now()));
    }

    // Se quiser usar JWT como refresh token (não persistido), mantenha este método. Caso contrário, remova.
    public String generateRefreshToken(UserPrincipal user) {
        return generateTokenWithClaims(user.getUsername(), refreshExpirationMs, Map.of(
                "id", user.getAccountId().toString()
        ));
    }

    private String generateTokenWithClaims(String subject, long expiration, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // aqui subject = email, se desejar trocar para userId, ajuste.
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // Útil para refresh token JWT com claim "id"
    public UUID extractUserId(String token) {
        String id = (String) parseClaims(token).get("id");
        return UUID.fromString(id);
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
