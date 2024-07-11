package com.example.EventHub.User;

import com.example.EventHub.Role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public class UserDTO {

    @NotEmpty(message = "Username cannot be empty!")
    @Size(min = 4, max = 20, message = "USername should be between 4 and 20")
    private String username;
    @NotEmpty(message = "Please enter email!")
    private String email;
    @NotEmpty(message = "Please enter first name!")
    private String firstName;
    @NotEmpty(message = "Please enter last name!")
    private String lastName;
    @NotEmpty(message = "Fill in the password!")
    @Size(min = 6, max = 20, message = "Password should be between 6 and 20")
    private String password;
    @NotEmpty(message = "Please confirm the password!")
    @Size(min =6, max = 20, message = "Password should be between 6 and 20")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }



}
