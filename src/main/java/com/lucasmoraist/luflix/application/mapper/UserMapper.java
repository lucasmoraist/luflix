package com.lucasmoraist.luflix.application.mapper;

import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.entity.UserEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toDomain(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }

}
