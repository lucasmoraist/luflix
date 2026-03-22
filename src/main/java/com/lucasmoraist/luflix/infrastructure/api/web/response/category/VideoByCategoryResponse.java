package com.lucasmoraist.luflix.infrastructure.api.web.response.category;

public record VideoByCategoryResponse(
        VideoResponse video
) {

    public record VideoResponse(
            Long id,
            Long categoryId,
            String title,
            String description,
            String url
    ){}
}
