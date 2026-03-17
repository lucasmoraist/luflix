package com.lucasmoraist.luflix.infrastructure.api.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,
        @NotBlank(message = "Color is required")
        @Size(max = 50, message = "Color must be at most 50 characters")
        String color
) {

}
