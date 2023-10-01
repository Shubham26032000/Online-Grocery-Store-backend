package com.virtusa.online_grocery_store.services;



import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.UserNotFoundException;
import com.virtusa.online_grocery_store.pojo.Login;
import com.virtusa.online_grocery_store.repositories.UserRepository;
import com.virtusa.online_grocery_store.security.UserFromJwtToken;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private UserFromJwtToken userFromJwtToken = new UserFromJwtToken();

    @Override
    public User createUser(User user) throws Exception {
        User existedUser = userRepository.findByEmail(user.getEmail());
        if(existedUser != null)
            throw new Exception("Email ID already exist!!");
        User userEntity = userRepository.save(user);
        logger.info("User save ! "+userEntity);
        return userEntity;
    }

    @Override
    public User getUserByEmailAndPassword(Login user) {
        String email = user.getEmail();
        String password = user.getPassword();
        return userRepository.findByEmailAndPassword(email,password);
    }


    @Override
    public User updateUser( String jwtToken, User user) {

           User currentUser = getUserFromToken(jwtToken);
            if(user.getUserName() != null)
                currentUser.setUserName(user.getUserName());
            if (user.getPassword() != null)
                currentUser.setPassword(user.getPassword());
            if(user.getMobileNumber() != null)
                currentUser.setMobileNumber(user.getMobileNumber());
            if(user.getAddress() != null)
                currentUser.setAddress(user.getAddress());
        return userRepository.save(currentUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String jwtToken) {
        return getUserFromToken(jwtToken);
    }

    @Override
    public List<User> findUserByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User getUserFromToken(String jwtToken) {
        String token = jwtToken.substring(7);
        String email = jwtUtil.extractUsername(token);
        return userRepository.findByEmail(email);
    }



}
