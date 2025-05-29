package com.tutorial.project.logic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User  implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    private Role role;
//    user details
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }
//  getPassword
    @Override
    public String getPassword(){
        return password;
    }
//get username
    @Override
    public String getUsername(){
        return email;
    }
//    isExpired
    public boolean isAccountNonExpired(){
        return true;
    }
//    is locked
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
// credential
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
//    is enabled
    public boolean isEnabled(){
        return true;
    }
}
