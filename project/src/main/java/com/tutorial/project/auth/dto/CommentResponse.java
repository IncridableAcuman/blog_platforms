package com.tutorial.project.auth.dto;

import com.tutorial.project.auth.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
    private Post post;
}
