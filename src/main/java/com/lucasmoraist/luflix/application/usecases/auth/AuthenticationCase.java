package com.lucasmoraist.luflix.application.usecases.auth;

import com.lucasmoraist.luflix.domain.exceptions.AuthenticationException;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationCase {

    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthenticationCase(UserPersistence userPersistence, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String execute(String email, String password) {
        User user = this.userPersistence.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new AuthenticationException("Email or password is incorrect");
                });

        if (!passwordEncoder.matches(password, user.password())) {
            log.warn("Invalid password for user with email: {}", email);
            throw new AuthenticationException("Email or password is incorrect");
        }

        return tokenService.generateToken(user);
    }


}
