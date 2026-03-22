package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.application.mapper.UserMapper;
import com.lucasmoraist.luflix.domain.exceptions.UniqueException;
import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.entity.UserEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UserPersistence {

    private final UserRepository userRepository;

    public UserPersistence(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    public User save(User user) {
        try {
            UserEntity entity = UserEntity.builder()
                    .name(user.name())
                    .email(user.email())
                    .password(user.password())
                    .role(user.role())
                    .build();

            UserEntity savedEntity = this.userRepository.save(entity);
            log.debug("User saved with id: {}", savedEntity.getId());
            return UserMapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Email {} already exists", user.email());
            throw new UniqueException("Email already exists: " + user.email());
        }
    }

}
