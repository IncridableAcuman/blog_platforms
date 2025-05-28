//package com.tutorial.project.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CorsLoggingFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        System.out.println("CORS Request: " + req.getMethod() + " " + req.getRequestURI());
//        System.out.println("Origin: " + req.getHeader("Origin"));
//        chain.doFilter(request, response);
//    }
//}
