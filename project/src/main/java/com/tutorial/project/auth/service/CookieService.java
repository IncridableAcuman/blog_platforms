package com.tutorial.project.auth.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {
//    add to cookie
    public void addTokenToCookie(HttpServletResponse response,String refreshToken){
        ResponseCookie cookie=ResponseCookie.from("token",refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(604800000)
                .build();
        response.addHeader("Set-Cookie",cookie.toString());
    }
//    delete refresh token from cookie
public void clearCookie(HttpServletResponse response){
    Cookie cookie=new Cookie("token",null);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
}
}
