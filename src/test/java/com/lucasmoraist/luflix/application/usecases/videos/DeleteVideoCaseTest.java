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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteVideoCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    DeleteVideoCase deleteVideoCase;

    @Test
    @DisplayName("Should delete a video successfully")
    void case01() {
        Long videoId = 1L;
        VideoEntity entity = mock(VideoEntity.class);

        when(videoPersistence.findById(any())).thenReturn(Optional.of(entity));

        deleteVideoCase.execute(videoId);

        verify(videoPersistence, times(1)).findById(videoId);
        verify(videoPersistence, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("Should throw an exception when video not found")
    void case02() {
        Long videoId = 1L;

        when(videoPersistence.findById(any())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> deleteVideoCase.execute(videoId));

        verify(videoPersistence, times(1)).findById(videoId);
    }

}