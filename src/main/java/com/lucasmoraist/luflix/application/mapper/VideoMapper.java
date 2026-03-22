package com.lucasmoraist.luflix.application.mapper;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.response.category.VideoByCategoryResponse;
import com.lucasmoraist.luflix.infrastructure.api.web.response.video.FindAllVideoResponse;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VideoMapper {

    public static Video toDomain(VideoEntity videoEntity) {
        return new Video(
                videoEntity.getId(),
                videoEntity.getTitle(),
                videoEntity.getDescription(),
                videoEntity.getUrl(),
                videoEntity.getCategory() != null
                        ? CategoryMapper.toDomain(videoEntity.getCategory())
                        : null
        );
    }

    public static VideoByCategoryResponse fromVideoByCategoryResponse(VideoEntity videoEntity) {
        return new VideoByCategoryResponse(
                new VideoByCategoryResponse.VideoResponse(
                        videoEntity.getId(),
                        videoEntity.getCategory().getId(),
                        videoEntity.getTitle(),
                        videoEntity.getDescription(),
                        videoEntity.getUrl()
                )
        );
    }

    public static FindAllVideoResponse toResponse(VideoEntity entity) {
        return new FindAllVideoResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getUrl(),
                entity.getCategory() != null
                        ? CategoryMapper.toDomain(entity.getCategory())
                        : null
        );
    }

}
