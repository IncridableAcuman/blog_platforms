package com.tutorial.project.auth.repository;

import com.tutorial.project.auth.model.Token;
import com.tutorial.project.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByUser(User user);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByExpiryDate(Date expiryDate);
}
