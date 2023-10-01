package com.virtusa.online_grocery_store.pojo;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Login {

    @NotEmpty(message = "Please enter email address")
    @NotBlank(message = "Email must not blank!")
    @Email(message = "Please provide valid email address")
    private String email;
    @NotEmpty(message = "Please provide password")
    @NotBlank(message = "Password must not blank!")
    private String password;


    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
