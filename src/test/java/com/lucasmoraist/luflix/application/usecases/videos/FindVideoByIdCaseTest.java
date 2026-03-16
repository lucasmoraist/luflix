package com.lucasmoraist.luflix.application.usecases.videos;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindVideoByIdCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    FindVideoByIdCase findVideoByIdCase;

    @Test
    @DisplayName("Should find video by id")
    void case01() {
        long videoId = 1L;
        VideoEntity video = mock(VideoEntity.class);

        when(videoPersistence.findById(videoId)).thenReturn(Optional.of(video));

        findVideoByIdCase.execute(videoId);

        verify(videoPersistence, times(1)).findById(videoId);
    }

    @Test
    @DisplayName("Should throw exception when video not found")
    void case02() {
        long videoId = 1L;

        when(videoPersistence.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findVideoByIdCase.execute(videoId));

        verify(videoPersistence, times(1)).findById(videoId);
    }

}