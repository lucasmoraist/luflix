package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.application.mapper.VideoMapper;
import com.lucasmoraist.luflix.infrastructure.api.web.response.video.FindAllVideoResponse;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FindAllVideosFreeCase {

    private final VideoPersistence videoPersistence;

    public FindAllVideosFreeCase(VideoPersistence videoPersistence) {
        this.videoPersistence = videoPersistence;
    }

    public Page<FindAllVideoResponse> execute(int page, int size) {
        log.debug("Find all free videos with page {} and size {}", page, size);
        List<FindAllVideoResponse> videos = this.videoPersistence.findAll(null)
                .stream()
                .filter(it -> it.getCategory().getTitle().equals("Livre"))
                .map(VideoMapper::toResponse)
                .toList();

        return new PageImpl<>(videos, PageRequest.of(page, size), videos.size());
    }

}
