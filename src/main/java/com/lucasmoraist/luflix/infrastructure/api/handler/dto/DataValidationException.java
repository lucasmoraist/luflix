package com.lucasmoraist.luflix.infrastructure.api.handler.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

@Schema(description = "Detalhes de erro de validação")
public record DataValidationException(
        @Schema(description = "Campo que falhou na validação", example = "email")
        String field,
        @Schema(description = "Mensagem de erro", example = "Email is required")
        String message
) {
    public DataValidationException(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
