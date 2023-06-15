package com.projekt.projekt.Validation;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Person {

    @NotBlank(message = "Name can't be empty")
    @Size(min=3, max=20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Pattern(regexp ="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" , message = "Must be a well-formed email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 5, message = "password length must be at least 5 letters long")
    private String password;

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

}
