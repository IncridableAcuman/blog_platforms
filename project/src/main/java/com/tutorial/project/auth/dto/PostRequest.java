package com.tutorial.project.auth.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String author;
    private String image;
    private Double price;
    private String sourceUrl;
}
