package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.infrastructure.api.web.response.category.VideoByCategoryResponse;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FindVideoByCategoryIdCase {

    private final VideoPersistence videoPersistence;

    public FindVideoByCategoryIdCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public Page<VideoByCategoryResponse> findVideoByCategoryIdCase(Long categoryId, int page, int size) {
        log.info("Finding videos by category id: {}", categoryId);
        List<VideoEntity> entities = this.videoPersistence.findByCategoryId(categoryId);
        List<VideoByCategoryResponse> videos = entities
                .stream()
                .map(VideoByCategoryResponse::toResponse)
                .toList();

        return new PageImpl<>(videos, PageRequest.of(page, size), entities.size());
    }

}
