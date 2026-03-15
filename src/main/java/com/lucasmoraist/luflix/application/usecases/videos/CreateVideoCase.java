package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateVideoCase {

    private final VideoPersistence videoPersistence;

    public CreateVideoCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public Video execute(VideoRequest request) {
        log.info("Creating video with title: {}", request.title());
        Video video = videoPersistence.save(request);
        log.info("Video created with ID: {}", video.id());
        return video;
    }

}
