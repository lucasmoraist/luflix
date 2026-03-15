package com.lucasmoraist.luflix.domain.model;

import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;

public record Video(
        Long id,
        String title,
        String description,
        String url
) {

    public static Video toDomain(VideoEntity videoEntity) {
        return new Video(
                videoEntity.getId(),
                videoEntity.getTitle(),
                videoEntity.getDescription(),
                videoEntity.getUrl()
        );
    }

}
