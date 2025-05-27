package com.tutorial.project.logic.repository;

import com.tutorial.project.logic.model.Token;
import com.tutorial.project.logic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByUser(User user);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByExpiryDate(Date expiryDate);
}
