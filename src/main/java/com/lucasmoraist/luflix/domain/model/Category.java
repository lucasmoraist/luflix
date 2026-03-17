package com.lucasmoraist.luflix.domain.model;

import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;

public record Category(
        Long id,
        String title,
        String color
) {

    public static Category toDomain(CategoryEntity category) {
        return new Category(
                category.getId(),
                category.getTitle(),
                category.getColor()
        );
    }

}
