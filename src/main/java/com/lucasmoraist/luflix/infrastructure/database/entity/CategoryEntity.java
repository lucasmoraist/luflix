package com.lucasmoraist.luflix.infrastructure.database.entity;

import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_lf_category")
@Entity(name = "t_lf_category")
public class CategoryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String color;

    public void update(CategoryRequest request) {
        if (request.title() != null) this.title = request.title();
        if (request.color() != null) this.color = request.color();
    }

}
