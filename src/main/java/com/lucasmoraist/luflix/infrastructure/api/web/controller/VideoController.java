package com.lucasmoraist.luflix.infrastructure.api.web.controller;

import com.lucasmoraist.luflix.application.usecases.videos.CreateVideoCase;
import com.lucasmoraist.luflix.application.usecases.videos.DeleteVideoCase;
import com.lucasmoraist.luflix.application.usecases.videos.FindAllVideosCase;
import com.lucasmoraist.luflix.application.usecases.videos.FindVideoByIdCase;
import com.lucasmoraist.luflix.application.usecases.videos.UpdateVideoCase;
import com.lucasmoraist.luflix.domain.model.Video;
import com.lucasmoraist.luflix.infrastructure.api.web.request.VideoRequest;
import com.lucasmoraist.luflix.infrastructure.api.web.response.video.FindAllVideoResponse;
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
@RequestMapping("/api/v1/videos")
public class VideoController {

    private final FindAllVideosCase findAllVideosCase;
    private final FindVideoByIdCase findVideoByIdCase;
    private final CreateVideoCase createVideoCase;
    private final UpdateVideoCase updateVideoCase;
    private final DeleteVideoCase deleteVideoCase;

    @GetMapping
    public ResponseEntity<Page<FindAllVideoResponse>> getAllVideos(
            @RequestParam(required = false, value = "search") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<FindAllVideoResponse> videos = findAllVideosCase.execute(title, page, size);
        return ResponseEntity.ok().body(videos);
    }

    @GetMapping("{videoId}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long videoId) {
        Video video = findVideoByIdCase.execute(videoId);
        return ResponseEntity.ok().body(video);
    }

    @PostMapping
    public ResponseEntity<Void> createVideo(@Valid @RequestBody VideoRequest request) {
        Video video = createVideoCase.execute(request);
        URI location = URI.create(String.format("/api/v1/videos/%s", video.id()));
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("{videoId}")
    public ResponseEntity<Void> updateVideo(@PathVariable Long videoId, @RequestBody VideoRequest request) {
        updateVideoCase.execute(videoId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long videoId) {
        this.deleteVideoCase.execute(videoId);
        return ResponseEntity.noContent().build();
    }

}
