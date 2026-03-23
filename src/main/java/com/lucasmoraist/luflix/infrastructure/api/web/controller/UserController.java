package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.lucasmoraist.luflix.application.usecases.user.CreateUserCase;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.api.web.request.UserRequest;
import com.lucasmoraist.luflix.infrastructure.config.swagger.routes.UserSwagger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserSwagger {

    private final CreateUserCase createUserCase;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRequest request) {
        User user = this.createUserCase.execute(request);
        URI location = URI.create(String.format("/api/v1/users/%s", user.id()));
        return ResponseEntity.created(location).build();
    }

}
