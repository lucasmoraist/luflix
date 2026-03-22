package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateVideoCase {

    private final VideoPersistence videoPersistence;
    private final CategoryPersistence categoryPersistence;

    public CreateVideoCase(VideoPersistence videoPersistence, CategoryPersistence categoryPersistence) {
        this.videoPersistence = videoPersistence;
        this.categoryPersistence = categoryPersistence;
    }

    public Video execute(VideoRequest request) {
        Category category = (request.category() == null)
                ? this.categoryPersistence.findById(1L)
                    .map(Category::toDomain)
                    .orElseThrow(() -> new IllegalArgumentException("Default category not found"))
                : this.categoryPersistence.findByTitle(request.category().title())
                    .map(Category::toDomain)
                    .orElseGet(() -> this.categoryPersistence.save(request.category()));
        log.debug("Category found or created with title: {}", category.title());

        Video video = new Video(
                null,
                request.title(),
                request.description(),
                request.url(),
                category
        );

        log.info("Creating video with title: {}", request.title());
        Video videoSaved = videoPersistence.save(video);
        log.debug("Video created with ID: {}", video.id());
        return videoSaved;
    }

}
