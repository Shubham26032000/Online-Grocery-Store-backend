package com.virtusa.online_grocery_store.services;

import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.pojo.Login;

import java.util.List;


public interface UserService {
    User createUser(User user) throws Exception;
    User getUserByEmailAndPassword(Login login);
    User updateUser(String jwtToken, User user);

    List<User> getAllUsers();

    User getUserById(String jwtToken);

    List<User> findUserByRole(String role);


    User getUserFromToken(String jwtToken);
}