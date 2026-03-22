package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.lucasmoraist.luflix.application.usecases.auth.AuthenticationCase;
import com.lucasmoraist.luflix.infrastructure.api.web.request.AuthenticationRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.token.TokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Value("${spring.security.jwt.expiration-hours}")
    private Integer expirationHours;
    private final AuthenticationCase authenticationCase;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody AuthenticationRequest request) {
        String token = this.authenticationCase.execute(request.email(), request.password());
        TokenDTO tokenDTO = new TokenDTO(
                token,
                "Bearer",
                expirationHours
        );
        return ResponseEntity.ok().body(tokenDTO);
    }

}
