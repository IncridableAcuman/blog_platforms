package com.tutorial.project.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {
    private static final String secret=Base64.getEncoder().encodeToString("qwertyuiopasdfghjklzxcvbnm1234567890".getBytes(StandardCharsets.UTF_8));
    private static final long accessTime=900000;
    private static final long refreshTime=604800000;
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
}
