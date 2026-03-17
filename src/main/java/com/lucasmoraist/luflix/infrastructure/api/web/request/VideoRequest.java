package com.lucasmoraist.luflix.infrastructure.api.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VideoRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,
        String description,
        @NotBlank(message = "URL is required")
        @Size(max = 500, message = "URL must be at most 500 characters")
        String url,
        CategoryRequest category
) {

}
