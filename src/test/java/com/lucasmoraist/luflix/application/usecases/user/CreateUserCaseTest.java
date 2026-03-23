package com.lucasmoraist.luflix.application.usecases.user;

import com.lucasmoraist.luflix.domain.enums.Roles;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.api.web.request.UserRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserCaseTest {

    @Mock
    UserPersistence userPersistence;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    CreateUserCase createUserCase;

    @Test
    @DisplayName("Should create a new user successfully")
    void case01() {
        UserRequest request = new UserRequest("Lucas", "lucas@email.com", "password123");
        User expected = new User(1L, "Lucas", "lucas@email.com", "encodedPassword", Roles.USER);

        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(userPersistence.save(any(User.class))).thenReturn(expected);

        User response = createUserCase.execute(request);

        assertEquals(expected, response);
        verify(passwordEncoder, times(1)).encode(request.password());
        verify(userPersistence, times(1)).save(any(User.class));
    }

}