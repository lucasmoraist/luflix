package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.luflix.application.usecases.videos.CreateVideoCase;
import com.lucasmoraist.luflix.application.usecases.videos.DeleteVideoCase;
import com.lucasmoraist.luflix.application.usecases.videos.FindAllVideosCase;
import com.lucasmoraist.luflix.application.usecases.videos.FindAllVideosFreeCase;
import com.lucasmoraist.luflix.application.usecases.videos.FindVideoByIdCase;
import com.lucasmoraist.luflix.application.usecases.videos.UpdateVideoCase;
import com.lucasmoraist.luflix.domain.model.Category;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.CategoryRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.video.FindAllVideoResponse;
import com.lucasmoraist.luflix.infrastructure.security.service.CustomUserDetailsService;
import com.lucasmoraist.luflix.infrastructure.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VideoController.class)
@AutoConfigureMockMvc(addFilters = false)
class VideoControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;
    @MockitoBean
    FindAllVideosCase findAllVideosCase;
    @MockitoBean
    FindVideoByIdCase findVideoByIdCase;
    @MockitoBean
    CreateVideoCase createVideoCase;
    @MockitoBean
    UpdateVideoCase updateVideoCase;
    @MockitoBean
    DeleteVideoCase deleteVideoCase;
    @MockitoBean
    FindAllVideosFreeCase findAllVideosFreeCase;
    @MockitoBean
    CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    TokenService tokenService;

    Category defaultCategory;

    @BeforeEach
    void setUp() {
        defaultCategory = new Category(1L, "Default", "Default category");
    }

    @Test
    @DisplayName("Should return paginated list of videos when GET /api/v1/videos is called")
    void case01() throws Exception {
        FindAllVideoResponse r = new FindAllVideoResponse(1L, "Title 1", "http://url", defaultCategory);
        Page<FindAllVideoResponse> page = new PageImpl<>(List.of(r));

        when(findAllVideosCase.execute(any(), anyInt(), anyInt())).thenReturn(page);

        mvc.perform(get("/api/v1/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Title 1"))
                .andExpect(jsonPath("$.content[0].url").value("http://url"));
    }

    @Test
    @DisplayName("Should return video details when GET /api/v1/videos/{videoId} is called")
    void case02() throws Exception {
        Video v = new Video(1L, "Title 1", "Description", "http://url", defaultCategory);

        when(findVideoByIdCase.execute(1L)).thenReturn(v);

        mvc.perform(get("/api/v1/videos/{videoId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title 1"))
                .andExpect(jsonPath("$.url").value("http://url"));
    }

    @Test
    @DisplayName("Should create a new video and return 201 with Location header when POST /api/v1/videos is called")
    void case03() throws Exception {
        CategoryRequest catReq = new CategoryRequest(defaultCategory.title(), defaultCategory.color());
        VideoRequest req = new VideoRequest("New title", "desc", "http://new", catReq);
        Video created = new Video(42L, req.title(), req.description(), req.url(), defaultCategory);

        when(createVideoCase.execute(ArgumentMatchers.any(VideoRequest.class))).thenReturn(created);

        mvc.perform(post("/api/v1/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/videos/42"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when creating a video with invalid data")
    void case04() throws Exception {
        String invalid = "{\"description\":\"no title or url\"}";

        mvc.perform(post("/api/v1/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update an existing video and return 200 OK when PATCH /api/v1/videos/{videoId} is called")
    void case05() throws Exception {
        CategoryRequest catReq = new CategoryRequest(defaultCategory.title(), defaultCategory.color());
        VideoRequest req = new VideoRequest("Updated title", "desc", "http://updated", catReq);

        doNothing().when(updateVideoCase).execute(1L, req);

        mvc.perform(patch("/api/v1/videos/{videoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return 400 Bad Request when updating a video with invalid data")
    void case06() throws Exception {
        doNothing().when(deleteVideoCase).execute(1L);

        mvc.perform(delete("/api/v1/videos/{videoId}", 1L))
                .andExpect(status().isNoContent());
    }

}