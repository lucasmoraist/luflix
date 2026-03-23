package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.luflix.application.usecases.auth.AuthenticationCase;
import com.lucasmoraist.luflix.infrastructure.api.web.request.AuthenticationRequest;
import com.lucasmoraist.luflix.infrastructure.security.service.CustomUserDetailsService;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;
    @MockitoBean
    AuthenticationCase authenticationCase;
    @MockitoBean
    CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    TokenService tokenService;

    @Test
    @DisplayName("Should authenticate user and return token when POST /api/v1/auth/login is called")
    void case01() throws Exception {
        AuthenticationRequest req = new AuthenticationRequest("user@email.com", "password123");

        when(authenticationCase.execute(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn("jwt-token");

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
//                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.type").value("Bearer"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when authenticating with invalid data")
    void case02() throws Exception {
        String invalid = "{\"email\":\"\"}";

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());
    }

}