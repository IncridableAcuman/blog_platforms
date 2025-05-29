package com.tutorial.project.logic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Size(min = 2,max = 100,message = "Title must be between 2 and 100 characters")
    @Column(nullable = false)
    private String title;

    @Size(min = 10,max = 3000,message = "Content must be between 10 and 2500 characters")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Size(min = 3,max = 50,message = "Author must be between 3 and 50 characters")
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String sourceUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
