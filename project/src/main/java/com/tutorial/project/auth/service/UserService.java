package com.tutorial.project.auth.service;

import com.tutorial.project.auth.dto.RegisterRequest;
import com.tutorial.project.auth.model.Role;
import com.tutorial.project.auth.model.User;
import com.tutorial.project.auth.repository.UserRepository;
import com.tutorial.project.exception.BadRequestExceptionHandler;
import com.tutorial.project.exception.ResourceNotFoundExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestExceptionHandler("User already exist");
        }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }
//    fin use
    public User findUserFromDB(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundExceptionHandler("User not found!"));
    }
    public void validatePassword(String password,String userPassword){
        if(!passwordEncoder.matches(password,userPassword)){
            throw new BadRequestExceptionHandler("Invalid password!");
        }
    }
}
