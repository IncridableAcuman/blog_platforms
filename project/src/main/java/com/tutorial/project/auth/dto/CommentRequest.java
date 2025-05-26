package com.tutorial.project.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
