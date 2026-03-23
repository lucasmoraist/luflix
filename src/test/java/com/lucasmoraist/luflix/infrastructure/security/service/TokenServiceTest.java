package com.lucasmoraist.luflix.infrastructure.security.service;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.exceptions.JwtException;
import com.lucasmoraist.luflix.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    User user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secretKey", "secret");
        ReflectionTestUtils.setField(tokenService, "expirationHours", 2);

        user = new User(
                1L,
                "John Doe",
                "johndoe@example.com",
                "password123",
                Roles.USER
        );
    }

    @Test
    @DisplayName("Should generate a valid JWT token for a user")
    void case01() {
        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Should validate a valid JWT token and return the correct subject")
    void case02() {
        String token = tokenService.generateToken(user);
        String subject = tokenService.getSubject(token);

        assertEquals(user.email(), subject);
    }

    @Test
    @DisplayName("Should throw JwtException when validating an invalid JWT token")
    void case03() {
        assertThrows(JwtException.class, () -> tokenService.getSubject("token-invalido"));
    }

}