package com.lucasmoraist.luflix.infrastructure.database.entity;

import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
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
@Table(name = "t_lf_video")
@Entity(name = "t_lf_video")
public class VideoEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String url;

    public void update(VideoRequest request) {
        if (request.title() != null) this.title = request.title();
        if (request.description() != null) this.description = request.description();
        if (request.url() != null) this.url = request.url();
    }

}
