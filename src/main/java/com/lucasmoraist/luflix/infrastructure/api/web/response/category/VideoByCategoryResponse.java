package com.lucasmoraist.luflix.infrastructure.api.web.response.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com vídeo associado a uma categoria")
public record VideoByCategoryResponse(
        @Schema(description = "Dados do vídeo")
        VideoResponse video
) {

    @Schema(description = "Dados resumidos do vídeo na categoria")
    public record VideoResponse(
            @Schema(description = "ID do vídeo", example = "1")
            Long id,
            @Schema(description = "ID da categoria", example = "1")
            Long categoryId,
            @Schema(description = "Título do vídeo", example = "Introdução ao Spring Boot")
            String title,
            @Schema(description = "Descrição do vídeo", example = "Aprenda os conceitos básicos do Spring Boot")
            String description,
            @Schema(description = "URL do vídeo", example = "https://www.youtube.com/watch?v=example")
            String url
    ){}
}
