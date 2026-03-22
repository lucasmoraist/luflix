package com.lucasmoraist.luflix.application.mapper;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public static Category toDomain(CategoryEntity category) {
        return new Category(
                category.getId(),
                category.getTitle(),
                category.getColor()
        );
    }

    public static CategoryEntity toEntity(CategoryRequest category) {
        return new CategoryEntity(
                null,
                category.title(),
                category.color()
        );
    }

    public static CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
                category.id() != null ? category.id() : null,
                category.title(),
                category.color()
        );
    }

}
