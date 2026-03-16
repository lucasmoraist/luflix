package com.lucasmoraist.luflix.application.usecases.videos;

import com.lucasmoraist.luflix.infrastructure.database.persistence.VideoPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindAllVideosCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    FindAllVideosCase findAllVideosCase;

    @Test
    @DisplayName("Should find all videos with pagination")
    void case01() {
        int page = 0;
        int size = 10;

        findAllVideosCase.execute(page, size);

        verify(videoPersistence, times(1)).findAll();
    }

}