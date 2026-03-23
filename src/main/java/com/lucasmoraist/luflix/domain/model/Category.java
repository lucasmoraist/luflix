package com.lucasmoraist.luflix.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoria de vídeos")
public record Category(
        @Schema(description = "ID da categoria", example = "1")
        Long id,
        @Schema(description = "Título da categoria", example = "Tecnologia")
        String title,
        @Schema(description = "Cor da categoria", example = "#FF5733")
        String color
) {

}
