package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.application.mapper.CategoryMapper;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateVideoCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    CreateVideoCase createVideoCase;

    Category defaultCategory;

    @BeforeEach
    void setUp() {
        defaultCategory = new Category(1L, "Default", "Default category");
    }

    @Test
    @DisplayName("Should create a new video successfully")
    void case01() {
        VideoRequest request = new VideoRequest(
                "Test Video",
                "This is a test video",
                "http://example.com/video.mp4",
                null
        );
        Video videoExpected = new Video(
                1L,
                request.title(),
                request.description(),
                request.url(),
                defaultCategory
        );

        when(videoPersistence.save(any())).thenReturn(videoExpected);
        when(categoryPersistence.findById(1L)).thenReturn(Optional.of(CategoryMapper.toEntity(defaultCategory)));

        Video response = createVideoCase.execute(request);

        assertEquals(videoExpected, response);
        verify(videoPersistence, times(1)).save(any());
    }

}