package com.tutorial.project.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
    public AuthResponse(Long id,String username,String email,String accessToken,String refreshToken){
        this.id=id;
        this.username=username;
        this.email=email;
        this.accessToken=accessToken;
        this.refreshToken=refreshToken;
    }
}
