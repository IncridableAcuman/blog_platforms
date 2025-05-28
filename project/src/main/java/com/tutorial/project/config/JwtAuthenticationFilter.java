//package com.tutorial.project.config;
//
//import com.tutorial.project.logic.service.TokenService;
//import com.tutorial.project.logic.service.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Configuration
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final TokenService tokenService;
//    private final UserService userService;
//
//    public JwtAuthenticationFilter(TokenService tokenService,UserService userService) {
//        this.tokenService = tokenService;
//        this.userService=userService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            try {
//                String email = tokenService.extractEmailFromToken(token);
//                if (tokenService.validateToken(token)) {
//                    UserDetails userDetails = userService.loadUserByUsername(email); // UserService dan foydalaning
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            } catch (Exception e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
