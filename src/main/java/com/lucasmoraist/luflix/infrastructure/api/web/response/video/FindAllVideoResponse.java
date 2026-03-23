package com.lucasmoraist.luflix.infrastructure.api.web.response.video;

import com.lucasmoraist.luflix.domain.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com dados resumidos do vídeo")
public record FindAllVideoResponse(
        @Schema(description = "ID do vídeo", example = "1")
        Long id,
        @Schema(description = "Título do vídeo", example = "Introdução ao Spring Boot")
        String title,
        @Schema(description = "URL do vídeo", example = "https://www.youtube.com/watch?v=example")
        String url,
        @Schema(description = "Categoria do vídeo")
        Category category
) {

}
