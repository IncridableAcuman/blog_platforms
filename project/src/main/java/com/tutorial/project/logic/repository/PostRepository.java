package com.tutorial.project.logic.repository;

import com.tutorial.project.logic.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthor(String author);
}
