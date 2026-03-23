package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllVideosFreeCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    FindAllVideosFreeCase findAllVideosFreeCase;

    @Test
    @DisplayName("Should find all free videos with pagination")
    void case01() {
        CategoryEntity freeCategory = CategoryEntity.builder()
                .id(1L)
                .title("Livre")
                .color("#FFFFFF")
                .build();
        VideoEntity video = VideoEntity.builder()
                .id(1L)
                .title("Free Video")
                .description("A free video")
                .url("http://example.com/free")
                .category(freeCategory)
                .build();

        when(videoPersistence.findAll(null)).thenReturn(List.of(video));

        findAllVideosFreeCase.execute(0, 10);

        verify(videoPersistence, times(1)).findAll(null);
    }

}