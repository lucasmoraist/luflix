package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateVideoCase {

    private final VideoPersistence videoPersistence;

    public UpdateVideoCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public void execute(Long videoId, VideoRequest request) {
        VideoEntity video = videoPersistence.findById(videoId)
                .orElseThrow(() -> {
                    log.error("Video with id {} not found", videoId);
                    return new EntityNotFoundException("Video not found");
                });
        video.update(request);
        this.videoPersistence.update(video);
    }

}
