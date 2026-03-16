package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateVideoCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    CreateVideoCase createVideoCase;

    @Test
    @DisplayName("Should create a new video successfully")
    void case01() {
        VideoRequest request = new VideoRequest(
                "Test Video",
                "This is a test video",
                "http://example.com/video.mp4"
        );
        Video videoExpected = new Video(
                1L,
                request.title(),
                request.description(),
                request.url()
        );

        when(videoPersistence.save(any())).thenReturn(videoExpected);

        Video response = createVideoCase.execute(request);

        assertEquals(videoExpected, response);
        verify(videoPersistence, times(1)).save(any());
    }

}