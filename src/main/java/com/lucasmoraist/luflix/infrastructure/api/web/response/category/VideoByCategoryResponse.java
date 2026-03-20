package com.lucasmoraist.luflix.infrastructure.api.web.response.category;

import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;

public record VideoByCategoryResponse(
        VideoResponse video
) {

    public static VideoByCategoryResponse toResponse(VideoEntity videoEntity) {
        return new VideoByCategoryResponse(
                new VideoResponse(
                        videoEntity.getId(),
                        videoEntity.getCategory().getId(),
                        videoEntity.getTitle(),
                        videoEntity.getDescription(),
                        videoEntity.getUrl()
                )
        );
    }

    record VideoResponse(
            Long id,
            Long categoryId,
            String title,
            String description,
            String url
    ){}
}
