package com.lucasmoraist.luflix.domain.model;

public record Video(
        Long id,
        String title,
        String description,
        String url,
        Category category
) {

}
