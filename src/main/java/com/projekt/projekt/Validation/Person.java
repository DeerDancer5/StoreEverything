package com.projekt.projekt.Validation;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Person {

    @NotBlank(message = "Name is required")
    @Size(min=2, max=30, msg = "name too short")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Name is required")
    @Size(min = 7, max = 30, msg = "password length must be at least 7 letters long")
    private String password;

    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
