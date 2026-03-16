package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteVideoCase {

    private final VideoPersistence videoPersistence;

    public DeleteVideoCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public void execute(Long videoId) {
        log.info("Deleting video with id: {}", videoId);
        VideoEntity videoEntity = videoPersistence.findById(videoId)
                .orElseThrow(() -> {
                    log.warn("Video not found with id: {}", videoId);
                    return new EntityNotFoundException("Video not found with id: " + videoId);
                });
        videoPersistence.delete(videoEntity);
    }

}
