package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.exceptions.UniqueException;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPersistenceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserPersistence userPersistence;

    @Test
    @DisplayName("Should return user by email when findByEmail is called")
    void case01() {
        UserEntity entity = UserEntity.builder()
                .id(1L)
                .name("Lucas")
                .email("lucas@email.com")
                .password("encodedPassword")
                .role(Roles.USER)
                .build();

        when(userRepository.findByEmail("lucas@email.com")).thenReturn(Optional.of(entity));

        Optional<User> result = userPersistence.findByEmail("lucas@email.com");

        assertTrue(result.isPresent());
        assertEquals("lucas@email.com", result.get().email());
        verify(userRepository, times(1)).findByEmail("lucas@email.com");
    }
    @Test
    @DisplayName("Should return empty when user not found by email")
    void case02() {
        when(userRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<User> result = userPersistence.findByEmail("notfound@email.com");

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByEmail("notfound@email.com");
    }
    @Test
    @DisplayName("Should save user and return domain object when save is called")
    void case03() {
        User user = new User(null, "Lucas", "lucas@email.com", "encodedPassword", Roles.USER);
        UserEntity savedEntity = UserEntity.builder()
                .id(10L)
                .name(user.name())
                .email(user.email())
                .password(user.password())
                .role(user.role())
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        User result = userPersistence.save(user);
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository, times(1)).save(captor.capture());

        UserEntity captured = captor.getValue();
        assertEquals(user.name(), captured.getName());
        assertEquals(user.email(), captured.getEmail());
        assertEquals(user.password(), captured.getPassword());
        assertEquals(user.role(), captured.getRole());
        assertNotNull(result);
    }
    @Test
    @DisplayName("Should throw UniqueException when email already exists")
    void case04() {
        User user = new User(null, "Lucas", "lucas@email.com", "encodedPassword", Roles.USER);

        when(userRepository.save(any(UserEntity.class))).thenThrow(new DataIntegrityViolationException("Duplicate email"));

        assertThrows(UniqueException.class, () -> userPersistence.save(user));
        
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

}