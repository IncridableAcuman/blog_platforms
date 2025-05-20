package com.tutorial.project.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {
    public void addTokenToCookie(HttpServletResponse response,String refreshToken){
        ResponseCookie cookie=ResponseCookie.from("token",refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(604800000)
                .build();
        response.addHeader("Set-Cookie",cookie.toString());
    }
}
