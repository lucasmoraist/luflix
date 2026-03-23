package com.lucasmoraist.luflix.infrastructure.filter;

import com.lucasmoraist.luflix.infrastructure.security.service.CustomUserDetailsService;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    TokenService tokenService;
    @Mock
    CustomUserDetailsService customUserDetailsService;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;

    @InjectMocks
    SecurityFilter securityFilter;

    @BeforeEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should continue filter chain without authentication when no token is provided")
    void case01() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verifyNoInteractions(tokenService);
    }

    @Test
    @DisplayName("Should authenticate user and continue filter chain when valid token is provided")
    void case02() throws ServletException, IOException {
        String token = "valid-token";
        String email = "lucas@teste.com";
        UserDetails userDetails = mock(UserDetails.class);

        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenService.getSubject(token)).thenReturn(email);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(tokenService).getSubject(token);
        verify(filterChain).doFilter(request, response);

        assert SecurityContextHolder.getContext().getAuthentication() != null;
    }

}