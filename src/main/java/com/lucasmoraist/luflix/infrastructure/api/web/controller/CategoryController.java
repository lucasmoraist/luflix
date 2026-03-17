package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.lucasmoraist.luflix.application.usecases.category.CreateCategoryCase;
import com.lucasmoraist.luflix.application.usecases.category.DeleteCategoryCase;
import com.lucasmoraist.luflix.application.usecases.category.FindAllCategoriesCase;
import com.lucasmoraist.luflix.application.usecases.category.FindCategoryByIdCase;
import com.lucasmoraist.luflix.application.usecases.category.UpdateCategoryCase;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final FindAllCategoriesCase findAllCategoriesCase;
    private final FindCategoryByIdCase findCategoryByIdCase;
    private final CreateCategoryCase createCategoryCase;
    private final UpdateCategoryCase updateCategoryCase;
    private final DeleteCategoryCase deleteCategoryCase;

    @GetMapping
    public ResponseEntity<Page<Category>> getAllVideos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Category> category = this.findAllCategoriesCase.execute(page, size);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getVideoById(@PathVariable Long categoryId) {
        Category video = findCategoryByIdCase.execute(categoryId);
        return ResponseEntity.ok().body(video);
    }

    @PostMapping
    public ResponseEntity<Void> createVideo(@Valid @RequestBody CategoryRequest request) {
        Category video = createCategoryCase.execute(request);
        URI location = URI.create(String.format("/api/v1/videos/%s", video.id()));
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("{categoryId}")
    public ResponseEntity<Void> updateVideo(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        updateCategoryCase.execute(categoryId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long categoryId) {
        this.deleteCategoryCase.execute(categoryId);
        return ResponseEntity.noContent().build();
    }

}
