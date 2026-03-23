package com.lucasmoraist.luflix.infrastructure.api.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação ou atualização de categoria")
public record CategoryRequest(
        @Schema(description = "Título da categoria", example = "Tecnologia", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,
        @Schema(description = "Cor da categoria em formato hexadecimal", example = "#FF5733", maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Color is required")
        @Size(max = 50, message = "Color must be at most 50 characters")
        String color
) {

}
