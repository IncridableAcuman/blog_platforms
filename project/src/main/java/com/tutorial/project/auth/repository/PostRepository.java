package com.tutorial.project.auth.repository;

import com.tutorial.project.auth.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
