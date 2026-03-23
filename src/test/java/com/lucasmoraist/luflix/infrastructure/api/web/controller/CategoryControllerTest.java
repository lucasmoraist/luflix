package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.luflix.application.usecases.category.CreateCategoryCase;
import com.lucasmoraist.luflix.application.usecases.category.DeleteCategoryCase;
import com.lucasmoraist.luflix.application.usecases.category.FindAllCategoriesCase;
import com.lucasmoraist.luflix.application.usecases.category.FindCategoryByIdCase;
import com.lucasmoraist.luflix.application.usecases.category.FindVideoByCategoryIdCase;
import com.lucasmoraist.luflix.application.usecases.category.UpdateCategoryCase;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.category.VideoByCategoryResponse;
import com.lucasmoraist.luflix.infrastructure.security.service.CustomUserDetailsService;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;
    @MockitoBean
    FindAllCategoriesCase findAllCategoriesCase;
    @MockitoBean
    FindCategoryByIdCase findCategoryByIdCase;
    @MockitoBean
    CreateCategoryCase createCategoryCase;
    @MockitoBean
    UpdateCategoryCase updateCategoryCase;
    @MockitoBean
    DeleteCategoryCase deleteCategoryCase;
    @MockitoBean
    FindVideoByCategoryIdCase findVideoByCategoryIdCase;
    @MockitoBean
    CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    TokenService tokenService;

    @Test
    @DisplayName("Should return paginated list of categories when GET /api/v1/categories is called")
    void case01() throws Exception {
        Category category = new Category(1L, "Technology", "#FF5733");
        Page<Category> page = new PageImpl<>(List.of(category));

        when(findAllCategoriesCase.execute(0, 10)).thenReturn(page);

        mvc.perform(get("/api/v1/categories").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Technology"))
                .andExpect(jsonPath("$.content[0].color").value("#FF5733"));
    }

    @Test
    @DisplayName("Should return category details when GET /api/v1/categories/{categoryId} is called")
    void case02() throws Exception {
        Category category = new Category(1L, "Technology", "#FF5733");

        when(findCategoryByIdCase.execute(1L)).thenReturn(category);

        mvc.perform(get("/api/v1/categories/{categoryId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Technology"))
                .andExpect(jsonPath("$.color").value("#FF5733"));
    }

    @Test
    @DisplayName("Should return videos by category when GET /api/v1/categories/{categoryId}/videos is called")
    void case03() throws Exception {
        VideoByCategoryResponse response = new VideoByCategoryResponse(
                new VideoByCategoryResponse.VideoResponse(1L, 1L, "Video Title", "Description", "http://url")
        );
        Page<VideoByCategoryResponse> page = new PageImpl<>(List.of(response));

        when(findVideoByCategoryIdCase.findVideoByCategoryIdCase(1L, 0, 10)).thenReturn(page);

        mvc.perform(get("/api/v1/categories/{categoryId}/videos", 1L)
                        .param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].video.id").value(1))
                .andExpect(jsonPath("$.content[0].video.title").value("Video Title"));
    }

    @Test
    @DisplayName("Should create a new category and return 201 with Location header when POST /api/v1/categories is called")
    void case04() throws Exception {
        CategoryRequest req = new CategoryRequest("Technology", "#FF5733");
        Category created = new Category(42L, req.title(), req.color());

        when(createCategoryCase.execute(ArgumentMatchers.any(CategoryRequest.class))).thenReturn(created);

        mvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/videos/42"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when creating a category with invalid data")
    void case05() throws Exception {
        String invalid = "{\"color\":\"#FF5733\"}";

        mvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update an existing category and return 200 OK when PATCH /api/v1/categories/{categoryId} is called")
    void case06() throws Exception {
        CategoryRequest req = new CategoryRequest("Updated Title", "#000000");

        doNothing().when(updateCategoryCase).execute(1L, req);

        mvc.perform(patch("/api/v1/categories/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete a category and return 204 No Content when DELETE /api/v1/categories/{categoryId} is called")
    void case07() throws Exception {
        doNothing().when(deleteCategoryCase).execute(1L);

        mvc.perform(delete("/api/v1/categories/{categoryId}", 1L))
                .andExpect(status().isNoContent());
    }

}