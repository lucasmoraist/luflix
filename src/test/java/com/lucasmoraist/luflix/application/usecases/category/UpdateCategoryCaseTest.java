package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
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
class UpdateCategoryCaseTest {

    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    UpdateCategoryCase updateCategoryCase;

    @Test
    @DisplayName("Should update category successfully")
    void case01() {
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest("Updated Title", "#000000");
        CategoryEntity categoryEntity = mock(CategoryEntity.class);

        when(categoryPersistence.findById(categoryId)).thenReturn(Optional.of(categoryEntity));

        updateCategoryCase.execute(categoryId, request);

        verify(categoryPersistence, times(1)).findById(any());
        verify(categoryEntity, times(1)).update(any());
        verify(categoryPersistence, times(1)).update(any());
    }

    @Test
    @DisplayName("Should throw exception when category not found")
    void case02() {
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest("Updated Title", "#000000");

        when(categoryPersistence.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> updateCategoryCase.execute(categoryId, request));

        verify(categoryPersistence, times(1)).findById(any());
        verify(categoryPersistence, times(0)).update(any());
    }

}