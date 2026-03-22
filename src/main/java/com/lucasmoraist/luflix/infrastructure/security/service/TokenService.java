package com.lucasmoraist.luflix.infrastructure.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lucasmoraist.luflix.domain.exceptions.JwtException;
import com.lucasmoraist.luflix.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TokenService {

    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration-hours}")
    private Integer expirationHours;
    private static final String ISSUER = "luflix";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(Date.from(Instant.now()))
                    .withJWTId(UUID.randomUUID().toString())
                    .withAudience(ISSUER)
                    .withSubject(user.email())
                    .withClaim("role", user.role().name())
                    .withExpiresAt(Date.from(this.generateExpirationDate()))
                    .sign(algorithm);
        } catch(JWTCreationException e){
            log.warn("Error while generating token for user {}: {}", user.email(), e.getMessage(), e);
            throw new JwtException("Error while generating token", e);
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException e) {
            log.warn("Error while verifying token: {}", e.getMessage(), e);
            throw new JwtException("Invalid or expired token", e);
        }
    }

    public String getClaim(String token, String claim) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getClaim(claim)
                    .asString();
        } catch(JWTVerificationException e) {
            log.warn("Error while verifying token: {}", e.getMessage(), e);
            throw new JwtException("Invalid or expired token", e);
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(expirationHours).toInstant(ZoneOffset.of("-03:00"));
    }

}
