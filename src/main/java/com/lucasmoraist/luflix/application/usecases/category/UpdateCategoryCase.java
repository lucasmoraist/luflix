package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateCategoryCase {

    private final CategoryPersistence categoryPersistence;

    public UpdateCategoryCase(CategoryPersistence categoryPersistence) {
        this.categoryPersistence = categoryPersistence;
    }

    public void execute(Long categoryId, CategoryRequest request) {
        log.info("Updating category with id: {}", categoryId);
        CategoryEntity categoryEntity = categoryPersistence.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category with id {} not found", categoryId);
                    return new EntityNotFoundException("Category not found");
                });
        categoryEntity.update(request);
        this.categoryPersistence.update(categoryEntity);
    }

}
