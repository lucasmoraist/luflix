package com.lucasmoraist.luflix.application.usecases.category;

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
class DeleteCategoryCaseTest {

    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    DeleteCategoryCase deleteCategoryCase;

    @Test
    @DisplayName("Should delete a category successfully")
    void case01() {
        Long categoryId = 1L;
        CategoryEntity entity = mock(CategoryEntity.class);

        when(categoryPersistence.findById(any())).thenReturn(Optional.of(entity));

        deleteCategoryCase.execute(categoryId);

        verify(categoryPersistence, times(1)).findById(categoryId);
        verify(categoryPersistence, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should throw an exception when category not found")
    void case02() {
        Long categoryId = 1L;

        when(categoryPersistence.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> deleteCategoryCase.execute(categoryId));

        verify(categoryPersistence, times(1)).findById(categoryId);
    }


}