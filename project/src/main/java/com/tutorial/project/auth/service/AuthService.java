package com.tutorial.project.auth.service;

import com.tutorial.project.auth.dto.AuthResponse;
import com.tutorial.project.auth.dto.LoginRequest;
import com.tutorial.project.auth.dto.RegisterRequest;
import com.tutorial.project.auth.model.Token;
import com.tutorial.project.auth.model.User;
import com.tutorial.project.auth.repository.TokenRepository;
import com.tutorial.project.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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
    private static final long accessTime=15*60*1000;
    private static final long refreshTime=7*24*60*60*1000;

//    register
    @Transactional
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
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("User already exist");
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
        token.setRefreshToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
        tokenRepository.save(token);
        cookieService.addTokenToCookie(response,refreshToken);
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),accessToken,refreshToken);
    }
//    login
    @Transactional
    public AuthResponse login(LoginRequest request,HttpServletResponse response){
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid password!");
        }
        String accessToken=tokenService.generateAccessToken(user.getEmail());
        String refreshToken=tokenService.generateRefreshToken(user.getEmail());
        Token token=new Token();
        token.setUser(user);
        token.setRefreshToken(refreshToken);
        token.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
        tokenRepository.save(token);
        cookieService.addTokenToCookie(response,token.getRefreshToken());
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),accessToken,refreshToken);
    }
//    refresh method
    @Transactional
    public AuthResponse refresh(String refreshToken ,HttpServletResponse response){
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
            throw new RuntimeException("Invalid token content!");
        }
        User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found!"));
        Token storedToken=tokenRepository.findByUser(user).orElseThrow(()->new RuntimeException("Invalid token!"));
        if(!storedToken.getRefreshToken().equals(refreshToken)){
            throw new RuntimeException("Token invalid mismatch!");
        }
        if(storedToken.getExpiryDate().before(new Date())){
            throw new RuntimeException("Refresh token expired!");
        }
        String newAccessToken=tokenService.generateAccessToken(email);
        //String newRefreshToken=tokenService.generateRefreshToken(email);
//        tokenRepository.delete(storedToken);
//        Token token1=new Token();
//        token1.setUser(user);
//        token1.setRefreshToken(newRefreshToken);
//        token1.setExpiryDate(new Date(System.currentTimeMillis()+refreshTime));
//        tokenRepository.save(token1);
        cookieService.addTokenToCookie(response,refreshToken);
        return new AuthResponse(user.getId(),user.getUsername(),user.getEmail(),newAccessToken,refreshToken);
    }
}
