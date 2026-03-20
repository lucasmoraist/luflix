package com.lucasmoraist.luflix.infrastructure.api.web.response.video;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;

public record FindAllVideoResponse(
        Long id,
        String title,
        String url,
        Category category
) {

    public static FindAllVideoResponse toResponse(VideoEntity entity) {
        return new FindAllVideoResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getUrl(),
                entity.getCategory() != null
                        ? Category.toDomain(entity.getCategory())
                        : null
        );
    }

}
