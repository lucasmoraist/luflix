package com.lucasmoraist.luflix.application.usecases.user;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.api.web.request.UserRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateUserCase {

    private final UserPersistence userPersistence;
    private final PasswordEncoder passwordEncoder;

    public CreateUserCase(UserPersistence userPersistence, PasswordEncoder passwordEncoder) {
        this.userPersistence = userPersistence;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(UserRequest request) {
        log.debug("Creating user with email: {}", request.email());
        User user = new User(
                null,
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Roles.USER
        );

        return this.userPersistence.save(user);
    }

}
