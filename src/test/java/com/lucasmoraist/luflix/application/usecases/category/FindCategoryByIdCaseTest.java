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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindCategoryByIdCaseTest {

    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    FindCategoryByIdCase findCategoryByIdCase;

    @Test
    @DisplayName("Should find category by id")
    void case01() {
        long categoryId = 1L;
        CategoryEntity entity = mock(CategoryEntity.class);

        when(categoryPersistence.findById(categoryId)).thenReturn(Optional.of(entity));

        findCategoryByIdCase.execute(categoryId);

        verify(categoryPersistence, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Should throw exception when category not found")
    void case02() {
        long categoryId = 1L;

        when(categoryPersistence.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findCategoryByIdCase.execute(categoryId));

        verify(categoryPersistence, times(1)).findById(categoryId);
    }

}