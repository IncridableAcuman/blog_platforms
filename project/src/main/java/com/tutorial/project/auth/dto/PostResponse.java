package com.tutorial.project.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
    public PostResponse(Long id,String title,String content,String author,String image,Double price,String sourceUrl){
        this.id=id;
        this.title=title;
        this.content=content;
        this.author=author;
        this.image=image;
        this.price=price;
        this.sourceUrl=sourceUrl;
    }
}
