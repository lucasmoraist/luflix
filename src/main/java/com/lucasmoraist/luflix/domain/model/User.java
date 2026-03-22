package com.lucasmoraist.luflix.domain.model;

import com.lucasmoraist.luflix.domain.enums.Roles;

public record User(
        Long id,
        String name,
        String email,
        String password,
        Roles role
) {

}
