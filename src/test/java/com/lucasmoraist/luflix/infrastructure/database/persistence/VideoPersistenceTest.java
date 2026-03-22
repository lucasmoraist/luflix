package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.application.mapper.CategoryMapper;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoPersistenceTest {

    @Mock
    VideoRepository videoRepository;
    @InjectMocks
    VideoPersistence videoPersistence;

    VideoEntity entity;
    Category defaultCategory;

    @BeforeEach
    void setUp() {
        defaultCategory = new Category(1L, "Default", "Default category");
        entity = VideoEntity.builder()
                .id(1L)
                .title("Test Video")
                .description("This is a test video.")
                .url("http://example.com/test-video")
                .build();
    }


    @Test
    @DisplayName("Should delete video by ID successfully")
    void case01() {
        videoPersistence.delete(entity);

        verify(videoRepository, times(1)).delete(entity);
    }

    @Test
    @DisplayName("Should return all videos when findAll is called")
    void case02() {
        VideoEntity entity2 = VideoEntity.builder()
                .id(2L)
                .title("Test Video")
                .description("This is a test video.")
                .url("http://example.com/test-video")
                .build();

        List<VideoEntity> entities = List.of(entity, entity2);
        when(videoRepository.findAll()).thenReturn(entities);

        List<VideoEntity> result = videoPersistence.findAll();

        assertEquals(entities, result);
        verify(videoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return video by ID when findById is called")
    void case03() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<VideoEntity> result = videoPersistence.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(videoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should save video and return domain object when save is called")
    void case04() {
        Video request = new Video(null, "My Title", "My Description", "http://video.url", defaultCategory);
        VideoEntity savedEntity = VideoEntity.builder()
                .id(10L)
                .title(request.title())
                .description(request.description())
                .url(request.url())
                .category(CategoryMapper.toEntity(defaultCategory))
                .build();

        when(videoRepository.save(any(VideoEntity.class))).thenReturn(savedEntity);

        Video result = videoPersistence.save(request);

        ArgumentCaptor<VideoEntity> captor = ArgumentCaptor.forClass(VideoEntity.class);
        verify(videoRepository, times(1)).save(captor.capture());
        VideoEntity captured = captor.getValue();
        assertEquals(request.title(), captured.getTitle());
        assertEquals(request.description(), captured.getDescription());
        assertEquals(request.url(), captured.getUrl());

        assertNotNull(result);
    }

    @Test
    @DisplayName("Should update video and call repository save when update is called")
    void case05() {
        videoPersistence.update(entity);

        verify(videoRepository, times(1)).save(entity);
    }

}