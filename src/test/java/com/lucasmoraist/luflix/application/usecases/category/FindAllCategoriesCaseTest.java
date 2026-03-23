package com.lucasmoraist.luflix.application.usecases.category;

import com.lucasmoraist.luflix.infrastructure.database.persistence.CategoryPersistence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FindAllCategoriesCaseTest {

    @Mock
    CategoryPersistence categoryPersistence;
    @InjectMocks
    FindAllCategoriesCase findAllCategoriesCase;

    @Test
    @DisplayName("Should find all categories with pagination")
    void case01() {
        int page = 0;
        int size = 10;

        findAllCategoriesCase.execute(page, size);

        verify(categoryPersistence, times(1)).findAll();
    }

}