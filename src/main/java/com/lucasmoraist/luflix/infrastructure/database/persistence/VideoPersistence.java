package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoPersistence {

    private final VideoRepository videoRepository;

    public VideoPersistence(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void delete(VideoEntity videoEntity) {
        this.videoRepository.delete(videoEntity);
    }

    public List<VideoEntity> findAll() {
        return this.videoRepository.findAll();
    }

    public List<VideoEntity> findByCategoryId(Long categoryId) {
        return this.videoRepository.findByCategoryId(categoryId);
    }

    public Optional<VideoEntity> findById(Long videoId) {
        return this.videoRepository.findById(videoId);
    }

    public Video save(VideoRequest request) {
        VideoEntity entity = VideoEntity.builder()
                .title(request.title())
                .description(request.description())
                .url(request.url())
                .build();

        VideoEntity savedEntity = this.videoRepository.save(entity);
        return Video.toDomain(savedEntity);
    }

    public void update(VideoEntity video) {
        this.videoRepository.save(video);
    }

}
