package com.tutorial.project.auth.repository;

import com.tutorial.project.auth.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthor(String author);
}
