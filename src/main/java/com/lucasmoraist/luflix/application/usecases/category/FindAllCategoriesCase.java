package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.application.mapper.CategoryMapper;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FindAllCategoriesCase {

    private final CategoryPersistence categoryPersistence;

    public FindAllCategoriesCase(CategoryPersistence categoryPersistence) {
        this.categoryPersistence = categoryPersistence;
    }

    public Page<Category> execute(int page, int size) {
        log.debug("Find all category with page {} and size {}", page, size);
        List<CategoryEntity> entities = this.categoryPersistence.findAll();
        List<Category> videos = entities
                .stream()
                .map(CategoryMapper::toDomain)
                .toList();

        return new PageImpl<>(videos, PageRequest.of(page, size), entities.size());
    }

}
