package com.tutorial.project.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required!")
    @Size(min = 3,max=50,message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private  String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8,message = "Password must be at least 8 characters")
    private String password;
}
