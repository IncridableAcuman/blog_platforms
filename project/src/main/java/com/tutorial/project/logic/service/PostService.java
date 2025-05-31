package com.tutorial.project.logic.service;

import com.tutorial.project.exception.BadRequestExceptionHandler;
import com.tutorial.project.logic.dto.PostRequest;
import com.tutorial.project.logic.dto.PostResponse;
import com.tutorial.project.logic.model.Post;
import com.tutorial.project.logic.repository.PostRepository;
import com.tutorial.project.exception.ResourceNotFoundExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
//     private final String UPLOAD_DIR="uploads/";

//    private String saveImage(MultipartFile multipartFile){
//         try {
//             String filename= UUID.randomUUID()+"_"+multipartFile.getOriginalFilename();
//             Path filePath= Paths.get(UPLOAD_DIR+filename);
//                 Files.createDirectories(filePath.getParent());
//             Files.copy(multipartFile.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
//             return filename;
//         } catch (IOException e){
//             throw new BadRequestExceptionHandler("Image upload failed: "+e.getMessage());
//         }
//  }

//    create a post
    public PostResponse createPost(PostRequest request){
    //    String imageFileName=saveImage(request.getImage());
        try {
            Post post=new Post();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setAuthor(request.getAuthor());
            post.setImage(request.getImage());
            post.setPrice(request.getPrice());
            post.setSourceUrl(request.getSourceUrl());
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return new PostResponse(post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor(),
                    post.getImage(),
                    post.getPrice(),
                    post.getSourceUrl(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }
//    get a post by id;
    public Post getAPostById(Long id){
        try {
            return postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found"));
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }
//    get  posts by author
    public List<Post> getAPostByAuthor(String author){
        try {
            return postRepository.findByAuthor(author);
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }
//    get all posts
    public List<Post> getAllPosts(){
        try {
            return postRepository.findAll();
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }
//    update a post
    public PostResponse updatePost(Long id,PostRequest request){
       //String fileImage=saveImage(request.getImage());
        try {
            Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found!"));
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setAuthor(request.getAuthor());
            post.setImage(request.getImage());
            post.setPrice(request.getPrice());
            post.setSourceUrl(request.getSourceUrl());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return new PostResponse(post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor(),
                    post.getImage(),
                    post.getPrice(),
                    post.getSourceUrl(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }
//    delete a post by id
    public void deletePost(Long id){
        try {
            Post post= postRepository.findById(id).orElseThrow(()->new ResourceNotFoundExceptionHandler("Post not found!"));
            postRepository.delete(post);
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }

}
