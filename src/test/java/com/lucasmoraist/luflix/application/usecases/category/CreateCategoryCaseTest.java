package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
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
class CreateCategoryCaseTest {

    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    CreateCategoryCase createCategoryCase;

    @Test
    @DisplayName("Should create a new category successfully")
    void case01() {
        CategoryRequest request = new CategoryRequest("Technology", "#FF5733");
        Category expected = new Category(1L, "Technology", "#FF5733");

        when(categoryPersistence.save(any(CategoryRequest.class))).thenReturn(expected);

        Category response = createCategoryCase.execute(request);

        assertEquals(expected, response);
        verify(categoryPersistence, times(1)).save(any(CategoryRequest.class));
    }

}