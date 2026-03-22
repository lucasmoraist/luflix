package com.lucasmoraist.luflix.infrastructure.database.repository;

import com.lucasmoraist.luflix.infrastructure.database.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    @Query("""
            SELECT v FROM t_lf_video v
            WHERE v.category.id = :categoryId
            """)
    List<VideoEntity> findByCategoryId(Long categoryId);
    List<VideoEntity> findByTitleContainingIgnoreCase(String title);

}
