package com.virtusa.online_grocery_store.services;

import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository repository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserName("shubham");
        user.setEmail("shubham@gmail.com");
        user.setPassword("shubham@123");
        user.setAddress("malvan");
        user.setMobileNumber("12345");
        user.setRole("ADMIN");
        user.setId(1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("GET USER BY USERNAME")
    public void  findUserByUsername(){

        String userName = "shubham";
//        User user = userService.getUserById(1L);
//        assertEquals(userName , user.getUserName());
    }

}