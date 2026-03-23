package com.lucasmoraist.luflix.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Vídeo da plataforma")
public record Video(
        @Schema(description = "ID do vídeo", example = "1")
        Long id,
        @Schema(description = "Título do vídeo", example = "Introdução ao Spring Boot")
        String title,
        @Schema(description = "Descrição do vídeo", example = "Aprenda os conceitos básicos do Spring Boot")
        String description,
        @Schema(description = "URL do vídeo", example = "https://www.youtube.com/watch?v=example")
        String url,
        @Schema(description = "Categoria do vídeo")
        Category category
) {

}
