package com.lucasmoraist.luflix.infrastructure.database.persistence;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class CategoryPersistenceTest {

    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryPersistence categoryPersistence;

    CategoryEntity entity;

    @BeforeEach
    void setUp() {
        entity = CategoryEntity.builder()
                .id(1L)
                .title("Technology")
                .color("#FF5733")
                .build();
    }

    @Test
    @DisplayName("Should delete category successfully")
    void case01() {
        categoryPersistence.delete(entity);

        verify(categoryRepository, times(1)).delete(entity);
    }

    @Test
    @DisplayName("Should return all categories when findAll is called")
    void case02() {
        CategoryEntity entity2 = CategoryEntity.builder()
                .id(2L)
                .title("Entertainment")
                .color("#00FF00")
                .build();
        List<CategoryEntity> entities = List.of(entity, entity2);

        when(categoryRepository.findAll()).thenReturn(entities);

        List<CategoryEntity> result = categoryPersistence.findAll();

        assertEquals(entities, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return category by ID when findById is called")
    void case03() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<CategoryEntity> result = categoryPersistence.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should save category and return domain object when save is called")
    void case04() {
        CategoryRequest request = new CategoryRequest("Technology", "#FF5733");
        CategoryEntity savedEntity = CategoryEntity.builder()
                .id(10L)
                .title(request.title())
                .color(request.color())
                .build();

        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedEntity);

        Category result = categoryPersistence.save(request);

        ArgumentCaptor<CategoryEntity> captor = ArgumentCaptor.forClass(CategoryEntity.class);
        verify(categoryRepository, times(1)).save(captor.capture());

        CategoryEntity captured = captor.getValue();
        assertEquals(request.title(), captured.getTitle());
        assertEquals(request.color(), captured.getColor());
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should update category and call repository save when update is called")
    void case05() {
        categoryPersistence.update(entity);

        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Should return category by title when findByTitle is called")
    void case06() {
        when(categoryRepository.findByTitle("Technology")).thenReturn(Optional.of(entity));

        Optional<CategoryEntity> result = categoryPersistence.findByTitle("Technology");

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(categoryRepository, times(1)).findByTitle("Technology");
    }

}