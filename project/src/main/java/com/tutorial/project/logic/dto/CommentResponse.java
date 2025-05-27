package com.tutorial.project.logic.dto;

import com.tutorial.project.logic.model.Post;
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
