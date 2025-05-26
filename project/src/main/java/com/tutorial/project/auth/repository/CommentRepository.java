package com.tutorial.project.auth.repository;

import com.tutorial.project.auth.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
