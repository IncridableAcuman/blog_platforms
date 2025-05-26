package com.tutorial.project.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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
    public PostResponse(Long id,
                        String title,
                        String content,
                        String author,
                        String image,
                        Double price,
                        String sourceUrl,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt
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
    }
}
