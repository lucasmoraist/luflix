package com.lucasmoraist.luflix.infrastructure.api.web.response.token;

public record TokenDTO(
        String token,
        String type,
        Integer expiresIn
) {

}
