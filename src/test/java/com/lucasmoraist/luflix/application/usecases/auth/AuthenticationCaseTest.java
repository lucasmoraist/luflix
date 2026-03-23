package com.lucasmoraist.luflix.application.usecases.auth;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.exceptions.AuthenticationException;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationCaseTest {

    @Mock
    UserPersistence userPersistence;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TokenService tokenService;
    @InjectMocks
    AuthenticationCase authenticationCase;

    @Test
    @DisplayName("Should authenticate user successfully and return token")
    void case01() {
        String email = "user@email.com";
        String password = "password123";
        User user = new User(1L, "User", email, "encodedPassword", Roles.USER);

        when(userPersistence.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.password())).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("jwt-token");

        String token = authenticationCase.execute(email, password);

        assertEquals("jwt-token", token);
        verify(userPersistence, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, user.password());
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void case02() {
        String email = "notfound@email.com";
        String password = "password123";

        when(userPersistence.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class, () -> authenticationCase.execute(email, password));

        verify(userPersistence, times(1)).findByEmail(email);
    }
    @Test
    @DisplayName("Should throw exception when password is incorrect")
    void case03() {
        String email = "user@email.com";
        String password = "wrongPassword";
        User user = new User(1L, "User", email, "encodedPassword", Roles.USER);

        when(userPersistence.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.password())).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> authenticationCase.execute(email, password));

        verify(userPersistence, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, user.password());
    }

}