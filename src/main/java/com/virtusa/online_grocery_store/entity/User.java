package com.virtusa.online_grocery_store.entity;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_name")
    @NotEmpty(message = "Please provide user name")
    private String userName;
    @Column(unique = true)
    @Email(message = "Please provide valid email address")
    private String email;
    @Column(name = "mobile_number")
    @NotEmpty( message = "Please provide phone number ")
    private String mobileNumber;

    @NotEmpty(message = "Password not empty please provide password")
    @Size(min = 3 , message = "Password atleast contains 3 characters")
    private String password;
    @Column(name = "role")
    private String role;

    @Column(name = "address")
    @NotEmpty(message = "Please provide address, address must not empty")
    private String address;

    public User() {
        this.role = "USER";
    }


    public User(String userName, String email, String mobileNumber, String password, String role) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.role = role;
    }

    public User(String userName, String email, String mobileNumber, String password, String role, String address) {
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
