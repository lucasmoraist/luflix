package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteCategoryCase {

    private final CategoryPersistence categoryPersistence;

    public DeleteCategoryCase(CategoryPersistence categoryPersistence) {
        this.categoryPersistence = categoryPersistence;
    }

    public void execute(Long categoryId) {
        log.info("Deleting category with id: {}", categoryId);
        CategoryEntity videoEntity = categoryPersistence.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found with id: {}", categoryId);
                    return new EntityNotFoundException("Category not found with id: " + categoryId);
                });
        categoryPersistence.delete(videoEntity);
    }

}
