package com.lucasmoraist.luflix.infrastructure.api.web.response.token;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token de autenticação JWT")
public record TokenDTO(
        @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,
        @Schema(description = "Tipo do token", example = "Bearer")
        String type,
        @Schema(description = "Tempo de expiração em horas", example = "24")
        Integer expiresIn
) {

}
