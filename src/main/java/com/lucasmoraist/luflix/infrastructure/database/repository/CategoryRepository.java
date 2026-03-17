package com.lucasmoraist.luflix.infrastructure.database.repository;

import com.lucasmoraist.luflix.infrastructure.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
