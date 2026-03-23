package com.lucasmoraist.luflix.infrastructure.api.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação ou atualização de vídeo")
public record VideoRequest(
        @Schema(description = "Título do vídeo", example = "Introdução ao Spring Boot", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be at most 255 characters")
        String title,
        @Schema(description = "Descrição do vídeo", example = "Aprenda os conceitos básicos do Spring Boot")
        String description,
        @Schema(description = "URL do vídeo", example = "https://www.youtube.com/watch?v=example", maxLength = 500, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "URL is required")
        @Size(max = 500, message = "URL must be at most 500 characters")
        String url,
        @Schema(description = "Categoria associada ao vídeo")
        CategoryRequest category
) {

}
