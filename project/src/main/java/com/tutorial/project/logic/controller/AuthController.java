package com.tutorial.project.logic.controller;

import com.tutorial.project.logic.dto.AuthResponse;
import com.tutorial.project.logic.dto.LoginRequest;
import com.tutorial.project.logic.dto.RegisterRequest;
import com.tutorial.project.logic.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response){
        return ResponseEntity.ok(authService.register(request,response));
    }
//    login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request,HttpServletResponse response){
        return ResponseEntity.ok(authService.login(request,response));
    }
//    refresh
    @GetMapping("/refresh")
public ResponseEntity<AuthResponse> refresh(
        @RequestHeader(name = "Authorization", required = false) String headerToken,
        @CookieValue(name = "token", required = false) String cookieToken,
        HttpServletResponse response) {
    String refreshToken = null;

    if (headerToken != null && headerToken.startsWith("Bearer ")) {
        refreshToken = headerToken.substring(7);
    } else if (cookieToken != null) {
        refreshToken = cookieToken;
    }

    return ResponseEntity.ok(authService.refresh(refreshToken, response));
}
//    logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name = "token",required = false) String refreshToken,HttpServletResponse response){
        authService.logout(refreshToken,response);
        return ResponseEntity.ok("User logged out");
    }
}
