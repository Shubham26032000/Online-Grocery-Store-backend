package com.virtusa.online_grocery_store.security;

import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFromJwtToken {


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    public UserFromJwtToken(){}

    public  long getUserIdFromJwtToken(String bearerToken)
    {
        String token = bearerToken.substring(7);
        String email = jwtUtil.extractUsername(token);

        User user = userRepository.findByEmail(email);
        return user.getId();
    }
}
