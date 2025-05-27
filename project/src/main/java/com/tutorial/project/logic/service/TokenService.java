package com.tutorial.project.logic.service;

import com.tutorial.project.logic.model.Token;
import com.tutorial.project.logic.model.User;
import com.tutorial.project.logic.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-time}")
    private  long accessTime;
    @Value("${jwt.refresh-time}")
    private long refreshTime;

    private final TokenRepository tokenRepository;
//generating key
    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
// generate token access and refresh token
    public String generateAccessToken(String email){
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+accessTime))
                .signWith(getSigningKey())
                .compact();
    }
//    refresh token
    public String generateRefreshToken(String email){
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+refreshTime))
                .signWith(getSigningKey())
                .compact();
    }
//    validate token
    public boolean validateToken(String refreshToken){
        try {
            Claims claims=Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(refreshToken).getBody();
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
//    extract email with token
    public String extractEmailFromToken(String refreshToken){
        Claims claims=Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        return claims.getSubject();
    }
//    create token
    public Token createToken(User user,String refreshToken){
        Token token=new Token();
        token.setUser(user);
        token.setRefreshToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
        return tokenRepository.save(token);
    }
}
