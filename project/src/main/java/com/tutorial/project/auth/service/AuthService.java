package com.tutorial.project.auth.service;

import com.tutorial.project.auth.dto.AuthResponse;
import com.tutorial.project.auth.dto.LoginRequest;
import com.tutorial.project.auth.dto.RegisterRequest;
import com.tutorial.project.auth.dto.TokenRequest;
import com.tutorial.project.auth.model.Token;
import com.tutorial.project.auth.model.User;
import com.tutorial.project.auth.repository.TokenRepository;
import com.tutorial.project.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;
    private static final long accessTime=900000;
    private static final long refreshTime=604800000;

//    register
    public AuthResponse register(RegisterRequest request, HttpServletResponse response){
        if(request.getEmail().isEmpty() ||
                request.getEmail()==null ||
                request.getUsername()==null ||
                request.getUsername().isEmpty() ||
                request.getPassword().isEmpty() ||
                request.getPassword()==null
        ){
         throw new RuntimeException("All field are required!");
        }
        //User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User already exist!"));
        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String accessToken=tokenService.generateAccessToken(user.getEmail());
        String refreshToken=tokenService.generateRefreshToken(user.getEmail());
        Token token=new Token();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
        tokenRepository.save(token);
        cookieService.addTokenToCookie(response,refreshToken);
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),accessToken,refreshToken);
    }
//    login
    public AuthResponse login(LoginRequest request,HttpServletResponse response){
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if(!user.getEmail().equals(request.getEmail())){
            throw new RuntimeException("User not found");
        }
        String accessToken=tokenService.generateAccessToken(user.getEmail());
        String refreshToken=tokenService.generateRefreshToken(user.getEmail());
        Token token=new Token();
        token.setUser(user);
        token.setToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
        tokenRepository.save(token);
        cookieService.addTokenToCookie(response,token.getToken());
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),accessToken,refreshToken);
    }
//    refresh method
    public AuthResponse refresh(String token ,HttpServletResponse response){
        if(!tokenService.validateToken(token)){
            throw new RuntimeException("Invalid token!");
        }
        String email=tokenService.extractEmailFromToken(token);
        if(email==null || email.isEmpty()){
            throw new RuntimeException("Invalid token");
        }
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found!"));
        Token token1=tokenRepository.findByUser(user).orElseThrow(()->new RuntimeException("Invalid token"));
        if(!token1.getToken().equals(token)){
            throw new RuntimeException("Token is mismatch");
        }
        String accessToken=tokenService.generateAccessToken(email);
        cookieService.addTokenToCookie(response,token);
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),accessToken,token);
    }
}
