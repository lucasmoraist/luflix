package com.lucasmoraist.luflix.infrastructure.api.web.response.video;

import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;

public record FindAllVideoResponse(
        Long id,
        String title,
        String url
) {

    public static FindAllVideoResponse toEntity(VideoEntity entity) {
        return new FindAllVideoResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getUrl()
        );
    }

}
