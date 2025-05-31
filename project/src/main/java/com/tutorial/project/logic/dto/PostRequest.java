package com.tutorial.project.logic.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String author;
    private String image;
    private Double price;
    private String sourceUrl;
}
