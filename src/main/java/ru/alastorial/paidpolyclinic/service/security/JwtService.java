package ru.alastorial.paidpolyclinic.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.entity.Patient;
import ru.alastorial.paidpolyclinic.security.PatientDetails;

import java.security.Key;
import java.time.Clock;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Setter
public class JwtService {


    private final Clock clock;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.token.validity-time}")
    private long tokenValidityInMilliseconds;
    private Key signInKey;

    // Передаем в функцию экстракта именно username в extractClaim
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // используем фукнцию расшифровки токена, получаем все клаимы, используем переданную функцию (в данном случае она извлекает username
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String generateToken(Patient patient) {
        return generateToken(new PatientDetails(patient));
    }

    public String generateToken(PatientDetails patientDetails) {
        return generateToken(Map.of("id", patientDetails.getPatient().getId()), patientDetails);
    }


    public String generateToken(
            Map<String, Object> extraClaims,
            PatientDetails patientDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(patientDetails.getUsername())
                .setIssuedAt(new Date(clock.millis()))
                .setExpiration(new Date(clock.millis() + tokenValidityInMilliseconds))
                .signWith(signInKey, SignatureAlgorithm.HS256)
                .compact();
    }


    //todo кидать исключение на expiredToken дорого. Почитать как сделать так, чтобы не кидать исключение
    public boolean isNotExpired(String token) {
        try {
            return !extractExpiration(token).before(new Date(clock.millis()));
        } catch (ExpiredJwtException expiredJwtException) {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.signInKey = Keys.hmacShaKeyFor(keyBytes);
    }
}
