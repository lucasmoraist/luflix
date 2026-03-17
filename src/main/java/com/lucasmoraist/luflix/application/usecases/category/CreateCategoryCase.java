package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateCategoryCase {

    private final CategoryPersistence categoryPersistence;

    public CreateCategoryCase(CategoryPersistence categoryPersistence) {
        this.categoryPersistence = categoryPersistence;
    }

    public Category execute(CategoryRequest request) {
        log.info("Creating category with name: {}", request.title());
        Category category = categoryPersistence.save(request);
        log.info("Category created with ID: {}", category.id());
        return category;
    }

}
