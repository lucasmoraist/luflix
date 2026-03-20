package com.lucasmoraist.luflix.infrastructure.config;

import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import com.lucasmoraist.luflix.infrastructure.database.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializingLuflix implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public InitializingLuflix(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.categoryRepository.findAll().isEmpty()) {
            CategoryEntity category1 = CategoryEntity.builder()
                    .title("Livre")
                    .color("#6c757d")
                    .build();

            this.categoryRepository.save(category1);
        }
    }

}
