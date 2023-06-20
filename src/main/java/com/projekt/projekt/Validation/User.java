package com.projekt.projekt.Validation;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @NotBlank(message = "Name can't be empty")
    @Size(min=3, max=20, message = "Name must be between 3 and 20 characters")
    private String username;

    @Pattern(regexp ="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" , message = "Must be a well-formed email address")
    private String email;

    @Size(min=3, max=20, message = "Name must be between 3 and 20 characters")
    @NotBlank(message = "Firstname can't be empty")
    private String firstname;

    @Size(min=3, max=50, message = "Name must be between 3 and 50 characters")
    @NotBlank(message = "Surname can't be empty")
    private String surname;

    @NotBlank(message = "password is required")
    @Size(min = 5, message = "password length must be at least 5 letters long")
    private String password;

    @NotBlank(message = "Age is required")
    @Pattern(regexp = "[0-9]+", message = "Must be a number")
    @Min(value = 18, message = "Must be at least 18 years old")
    private String age;
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(){

    }

    public User(String firstname, String surname, String username, String email, String password, String roles, String age) {
        this.firstname = firstname;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String name)
    {
        this.username = name;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
