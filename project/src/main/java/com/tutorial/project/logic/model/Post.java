package com.tutorial.project.logic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    @NotBlank(message = "Title is required!")
    @Size(min = 2,max = 100,message = "Title must be between 2 and 100 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content is required!")
    @Size(min = 10,max = 2500,message = "Content must be between 10 and 2500 characters")
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @NotBlank(message = "Author is required!")
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

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    @PrePersist
    protected void oneCreate(){
        this.createdAt=this.updatedAt=LocalDateTime.now();
    }
    @PreUpdate
    protected void oneUpdate(){
        this.updatedAt=LocalDateTime.now();
    }
}
