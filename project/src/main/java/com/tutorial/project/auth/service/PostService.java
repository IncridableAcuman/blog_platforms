package com.tutorial.project.auth.service;

import com.tutorial.project.auth.dto.PostRequest;
import com.tutorial.project.auth.dto.PostResponse;
import com.tutorial.project.auth.model.Post;
import com.tutorial.project.auth.repository.PostRepository;
import com.tutorial.project.exception.ResourceNotFoundExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

//    create a post
    public PostResponse createPost(PostRequest request){

    }
//    get a post by id;
    public Post getAPostById(Long id){
        return postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found"));
    }
//    get  posts by author
    public List<Post> getAPostByAuthor(String author){
        return postRepository.findByAuthor(author);
    }
//    get all posts
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
//    update a post
    public PostResponse updatePost(Long id,PostRequest request){

    }
//    delete a post by id
    public void deletePost(Long id){

    }

}
