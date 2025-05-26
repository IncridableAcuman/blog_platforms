package com.tutorial.project.auth.controller;

import com.tutorial.project.auth.dto.PostRequest;
import com.tutorial.project.auth.dto.PostResponse;
import com.tutorial.project.auth.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request){
        return ResponseEntity.ok(postService.createPost(request));
    }
}
