package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        entity = VideoEntity.builder()
                .id(1L)
                .title("Test Video")
                .description("This is a test video.")
                .url("http://example.com/test-video")
                .build();
    }


    @Test
    void deleteById_callsRepositoryDelete() {
        videoPersistence.delete(entity);

        verify(videoRepository, times(1)).delete(entity);
    }

    @Test
    void findAll_returnsAllEntities() {
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
    void findById_returnsOptionalEntity() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<VideoEntity> result = videoPersistence.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(videoRepository, times(1)).findById(1L);
    }

    @Test
    void save_persistsEntityAndReturnsDomain() {
        VideoRequest request = new VideoRequest("My Title", "My Description", "http://video.url");
        VideoEntity savedEntity = VideoEntity.builder()
                .id(10L)
                .title(request.title())
                .description(request.description())
                .url(request.url())
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
    void update_callsRepositorySaveWithEntity() {
        videoPersistence.update(entity);

        verify(videoRepository, times(1)).save(entity);
    }

}