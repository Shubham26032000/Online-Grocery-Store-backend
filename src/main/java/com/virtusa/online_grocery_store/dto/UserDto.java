package com.virtusa.online_grocery_store.dto;

import javax.validation.constraints.*;

public class UserDto {
    private long id;

    @NotEmpty(message = "Please provide user name")
    private String userName;

    @Email(message = "Please provide valid email address")
    private String email;

    @NotEmpty( message = "Please provide phone number ")
    @Pattern(regexp = "[6-9][0-9]{9}",message = "Please enter valid mobile number")
    @Size(min = 10,max = 10, message = "Phone Number must of 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Password not empty please provide password")
    @Size(min = 3 , message = "Password atleast contains 3 characters")
    private String password;

    private String role;

    @NotEmpty(message = "Please provide address, address must not empty")
    private String address;

    public UserDto() {
        this.role = "USER";
    }


    public UserDto(String userName, String email, String mobileNumber, String password, String role) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = role;
    }

    public UserDto(String userName, String email, String mobileNumber, String password, String role, String address) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = role;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

    }

}