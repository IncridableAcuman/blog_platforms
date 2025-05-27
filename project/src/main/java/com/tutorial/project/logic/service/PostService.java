package com.tutorial.project.logic.service;

import com.tutorial.project.logic.dto.PostRequest;
import com.tutorial.project.logic.dto.PostResponse;
import com.tutorial.project.logic.model.Comment;
import com.tutorial.project.logic.model.Post;
import com.tutorial.project.logic.repository.CommentRepository;
import com.tutorial.project.logic.repository.PostRepository;
import com.tutorial.project.exception.ResourceNotFoundExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

//    create a post
    public PostResponse createPost(PostRequest request){
        Post post=new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        post.setImage((request.getImage()));
        post.setPrice(request.getPrice());
        post.setSourceUrl(request.getSourceUrl());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        List<Comment> comments=post.getComments().stream().map(pst->{
            Comment comment=new Comment();
            comment.setText(pst.getText());
            comment.setAuthor(pst.getAuthor());
            comment.setCreateAt(pst.getCreateAt());
            comment.setPost(post);
            return commentRepository.save(comment);
        }).toList();
        return new PostResponse(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getImage(),
                post.getPrice(),
                post.getSourceUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments
        );
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
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found!"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        post.setImage(request.getImage());
        post.setPrice(request.getPrice());
        post.setSourceUrl(request.getSourceUrl());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        List<Comment> comments=post.getComments().stream().map(pst->{
            Comment comment=new Comment();
            comment.setText(pst.getText());
            comment.setAuthor(pst.getAuthor());
            comment.setCreateAt(pst.getCreateAt());
            comment.setPost(post);
            return commentRepository.save(comment);
        }).toList();
        return new PostResponse(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getImage(),
                post.getPrice(),
                post.getSourceUrl(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments
        );
    }
//    delete a post by id
    public void deletePost(Long id){
       Post post= postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found!"));
        postRepository.delete(post);
    }

}
