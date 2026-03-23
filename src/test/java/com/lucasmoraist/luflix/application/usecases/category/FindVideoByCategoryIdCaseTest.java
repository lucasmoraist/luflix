package com.lucasmoraist.luflix.application.usecases.category;

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
class FindVideoByCategoryIdCaseTest {

    @Mock
    VideoPersistence videoPersistence;
    @InjectMocks
    FindVideoByCategoryIdCase findVideoByCategoryIdCase;

    @Test
    @DisplayName("Should find videos by category id with pagination")
    void case01() {
        Long categoryId = 1L;
        int page = 0;
        int size = 10;

        findVideoByCategoryIdCase.findVideoByCategoryIdCase(categoryId, page, size);

        verify(videoPersistence, times(1)).findByCategoryId(categoryId);
    }

}