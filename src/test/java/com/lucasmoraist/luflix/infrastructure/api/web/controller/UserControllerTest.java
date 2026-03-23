package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.luflix.application.usecases.user.CreateUserCase;
import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.api.web.request.UserRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;
    @MockitoBean
    CreateUserCase createUserCase;
    @MockitoBean
    CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    TokenService tokenService;

    @Test
    @DisplayName("Should register a new user and return 201 with Location header when POST /api/v1/users/register is called")
    void case01() throws Exception {
        UserRequest req = new UserRequest("Lucas", "lucas@email.com", "password123");
        User created = new User(42L, req.name(), req.email(), "encodedPassword", Roles.USER);

        when(createUserCase.execute(ArgumentMatchers.any(UserRequest.class))).thenReturn(created);

        mvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/42"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when registering a user with invalid data")
    void case02() throws Exception {
        String invalid = "{\"name\":\"Lu\"}";

        mvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());
    }

}