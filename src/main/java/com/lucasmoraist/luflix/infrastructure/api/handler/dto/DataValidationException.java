package com.lucasmoraist.luflix.infrastructure.api.handler.dto;

import org.springframework.validation.FieldError;

public record DataValidationException(
        String field,
        String message
) {
    public DataValidationException(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
