package com.tutorial.project.logic.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {
    private String text;
    private String author;
    private LocalDateTime createdAt;
}
