package com.tutorial.project.logic.controller;

import com.tutorial.project.logic.dto.PostRequest;
import com.tutorial.project.logic.dto.PostResponse;
import com.tutorial.project.logic.model.Post;
import com.tutorial.project.logic.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
//    create a post
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request ){
        PostResponse response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }
//    get a post by id
    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getAPostById(@Valid @PathVariable Long id){
        return ResponseEntity.ok(postService.getAPostById(id));
    }
//    get posts by author
    @GetMapping("/get/authors/{author}")
    public ResponseEntity<List<Post>> getAPostByAuthor(@Valid @PathVariable String author){
        return ResponseEntity.ok(postService.getAPostByAuthor(author));
    }
//    get all post
    @GetMapping("/get/all")
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }
//    update a post
    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponse> updatePost(@Valid @PathVariable Long id,@Valid @RequestBody PostRequest request){
        return ResponseEntity.ok(postService.updatePost(id,request));
    }
//    delete a post by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@Valid @PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted");
    }
}
