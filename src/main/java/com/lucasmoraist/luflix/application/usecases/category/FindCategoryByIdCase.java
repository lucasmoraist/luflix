package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindCategoryByIdCase {

    private final CategoryPersistence categoryPersistence;

    public FindCategoryByIdCase(CategoryPersistence categoryPersistence) {
        this.categoryPersistence = categoryPersistence;
    }

    public Category execute(Long videoId) {
        log.info("Finding category by id: {}", videoId);
        return categoryPersistence.findById(videoId)
                .map(Category::toDomain)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", videoId);
                    return new EntityNotFoundException("Category not found with id: " + videoId);
                });
    }

}
