package com.tutorial.project.logic.service;

import com.tutorial.project.logic.dto.AuthResponse;
import com.tutorial.project.logic.dto.LoginRequest;
import com.tutorial.project.logic.dto.RegisterRequest;
import com.tutorial.project.logic.model.Token;
import com.tutorial.project.logic.model.User;
import com.tutorial.project.logic.repository.TokenRepository;
import com.tutorial.project.exception.BadRequestExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final CookieService cookieService;

    //    register
    @Transactional
    public AuthResponse register(RegisterRequest request, HttpServletResponse response){
        try {
            User user=userService.createUser(request);// create user
//        generate access,refresh tokens
            String accessToken=tokenService.generateAccessToken(user.getEmail());
            String refreshToken=tokenService.generateRefreshToken(user.getEmail());
//        create token and saving to db
            tokenService.createToken(user,refreshToken);
            cookieService.addTokenToCookie(response,refreshToken);
            return new AuthResponse(user.getId(),user.getUsername(),
                    user.getEmail(),user.getRole(),accessToken,refreshToken);
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }
    //    login
    @Transactional
    public AuthResponse login(LoginRequest request,HttpServletResponse response){
        try {
            User user=userService.findUserFromDB(request.getEmail());
            userService.validatePassword(request.getPassword(),user.getPassword());
            String accessToken=tokenService.generateAccessToken(user.getEmail());
            String refreshToken=tokenService.generateRefreshToken(user.getEmail());
//
            Token token=tokenRepository.findByUser(user).orElseGet(()-> tokenService.createToken(user,refreshToken));
            cookieService.addTokenToCookie(response,token.getRefreshToken());
            return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),
                    user.getRole(),accessToken,token.getRefreshToken());
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }
    //    refresh method
    @Transactional
    public AuthResponse refresh(String refreshToken ,HttpServletResponse response){
        try {
            if(refreshToken==null || refreshToken.isEmpty()){
                throw new RuntimeException("Token is required!");
            }
            if(!tokenService.validateToken(refreshToken)){
                throw new RuntimeException("Invalid token!");
            }
            String email;
            try {
                email=tokenService.extractEmailFromToken(refreshToken);
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            if(email==null || email.isEmpty()){
                throw new BadRequestExceptionHandler("Invalid token content!");
            }
            User user=userService.findUserFromDB(email);
            Token storedToken=tokenRepository.findByUser(user).orElseThrow(()->new BadRequestExceptionHandler("Invalid token!"));
            if(!storedToken.getRefreshToken().equals(refreshToken)){
                throw new BadRequestExceptionHandler("Token invalid mismatch!");
            }
            if(storedToken.getExpiryDate().before(new Date())){
                throw new RuntimeException("Refresh token expired!");
            }
            String newAccessToken=tokenService.generateAccessToken(email);
            cookieService.addTokenToCookie(response,refreshToken);
            return new AuthResponse(user.getId(),user.getUsername(),
                    user.getEmail(),user.getRole(),newAccessToken,refreshToken);
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }

    }
    //    logout
    @Transactional
    public void logout(String refreshToken,HttpServletResponse response){
        try {
            if(refreshToken==null || refreshToken.isEmpty()){
                throw new RuntimeException("Token is required!");
            }
            Token token=tokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new BadRequestExceptionHandler("Invalid token"));
            tokenRepository.delete(token);
            cookieService.clearCookie(response);
        } catch (RuntimeException e) {
            throw new BadRequestExceptionHandler(e.getMessage());
        }
    }
}