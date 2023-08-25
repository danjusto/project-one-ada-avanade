package com.ada_avanada.project_one.service;

import com.ada_avanada.project_one.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Shop Avanade-Ada")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Token generate failed", e);
        }
    }

    public String getSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Shop Avanade-Ada")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }
}
