package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.application.mapper.CategoryMapper;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryPersistence {

    private final CategoryRepository categoryRepository;

    public CategoryPersistence(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void delete(CategoryEntity videoEntity) {
        this.categoryRepository.delete(videoEntity);
    }

    public List<CategoryEntity> findAll() {
        return this.categoryRepository.findAll();
    }

    public Optional<CategoryEntity> findById(Long videoId) {
        return this.categoryRepository.findById(videoId);
    }

    public Category save(CategoryRequest request) {
        CategoryEntity entity = CategoryEntity.builder()
                .title(request.title())
                .color(request.color())
                .build();
        CategoryEntity savedEntity = this.categoryRepository.save(entity);
        return CategoryMapper.toDomain(savedEntity);
    }

    public void update(CategoryEntity categoryEntity) {
        this.categoryRepository.save(categoryEntity);
    }

    public Optional<CategoryEntity> findByTitle(String title) {
        return this.categoryRepository.findByTitle(title);
    }

}
