package com.tutorial.project.logic.dto;

import com.tutorial.project.logic.model.Comment;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String image;
    private Double price;
    private String sourceUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    List<Comment> comments;
    public PostResponse(Long id,
                        String title,
                        String content,
                        String author,
                        String image,
                        Double price,
                        String sourceUrl,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt,
                        List<Comment> comments
    ){
        this.id=id;
        this.title=title;
        this.content=content;
        this.author=author;
        this.image=image;
        this.price=price;
        this.sourceUrl=sourceUrl;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.comments=comments;
    }
}
