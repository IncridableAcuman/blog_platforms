package com.tutorial.project.logic.repository;

import com.tutorial.project.logic.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
