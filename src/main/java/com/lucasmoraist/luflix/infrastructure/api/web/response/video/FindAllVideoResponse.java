package com.lucasmoraist.luflix.infrastructure.api.web.response.video;

import com.lucasmoraist.luflix.domain.model.Category;

public record FindAllVideoResponse(
        Long id,
        String title,
        String url,
        Category category
) {

}
