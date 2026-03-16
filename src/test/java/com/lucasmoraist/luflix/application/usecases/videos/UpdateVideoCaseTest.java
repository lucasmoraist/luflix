package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateVideoCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    UpdateVideoCase updateVideoCase;

    @Test
    @DisplayName("Should update video successfully")
    void case01() {
        Long videoId = 1L;
        VideoRequest videoRequest = new VideoRequest(
                "New Title",
                "New Description",
                "http://example.com/new-video.mp4"
        );
        VideoEntity videoEntity = mock(VideoEntity.class);

        when(videoPersistence.findById(videoId)).thenReturn(Optional.of(videoEntity));

        updateVideoCase.execute(videoId, videoRequest);

        verify(videoPersistence, times(1)).findById(any());
        verify(videoEntity, times(1)).update(any());
        verify(videoPersistence, times(1)).update(any());
    }

    @Test
    @DisplayName("Should throw exception when video not found")
    void case02() {
        Long videoId = 1L;
        VideoRequest videoRequest = new VideoRequest(
                "New Title",
                "New Description",
                "http://example.com/new-video.mp4"
        );

        when(videoPersistence.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> updateVideoCase.execute(videoId, videoRequest));

        verify(videoPersistence, times(1)).findById(any());
        verify(videoPersistence, times(0)).update(any());
    }

}