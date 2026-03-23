package com.lucasmoraist.luflix.infrastructure.security.service;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserPersistence userPersistence;
    @InjectMocks
    CustomUserDetailsService service;

    @Test
    @DisplayName("Should load user by username successfully")
    void loadUserByUsernameSuccess() {
        User user = new User(
                1L,
                "John Doe",
                "johndoe@example.com",
                "password123",
                Roles.USER
        );

        when(userPersistence.findByEmail(any())).thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername(user.email());

        assertNotNull(details);
        assertEquals(user.email(), details.getUsername());
        verify(userPersistence, times(1)).findByEmail(user.email());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void loadUserByUsernameNotFound() {
        String email = "naoexiste@teste.com";
        when(userPersistence.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(email));
    }

}