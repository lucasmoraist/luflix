package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.application.mapper.VideoMapper;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindVideoByIdCase {

    private final VideoPersistence videoPersistence;

    public FindVideoByIdCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public Video execute(Long videoId) {
        log.info("Finding video by id: {}", videoId);
        return videoPersistence.findById(videoId)
                .map(VideoMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("Video not found with id: {}", videoId);
                    return new EntityNotFoundException("Video not found with id: " + videoId);
                });
    }

}
