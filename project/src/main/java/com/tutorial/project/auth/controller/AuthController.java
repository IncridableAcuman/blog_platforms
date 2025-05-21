package com.tutorial.project.auth.controller;

import com.tutorial.project.auth.dto.AuthResponse;
import com.tutorial.project.auth.dto.LoginRequest;
import com.tutorial.project.auth.dto.RegisterRequest;
import com.tutorial.project.auth.service.AuthService;
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
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authorization,HttpServletResponse response){
        String refreshToken=authorization.substring(7);
        return ResponseEntity.ok(authService.refresh(refreshToken,response));
    }
}
