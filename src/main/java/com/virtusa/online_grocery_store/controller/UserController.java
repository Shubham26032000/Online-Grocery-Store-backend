package com.virtusa.online_grocery_store.controller;

import com.virtusa.online_grocery_store.dto.UserDto;
import com.virtusa.online_grocery_store.entity.User;
import com.virtusa.online_grocery_store.exceptions.UserNotFoundException;
import com.virtusa.online_grocery_store.pojo.ApiResponse;
import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import com.virtusa.online_grocery_store.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getName());
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil util;



    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken,@RequestBody User user )
    {
        try {
            userService.updateUser(jwtToken, user);
        }catch (UserNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponse(true,"Wrong User ID "), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ApiResponse(true,"User updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<Object> getUserDetails(@RequestHeader(value = "authorization",defaultValue = "123") String jwtToken)
    {
        try {
            User user = userService.getUserFromToken(jwtToken);
        }catch (Exception e)
        {
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.OK);
        }

        return new ResponseEntity<>(userService.getUserFromToken(jwtToken),HttpStatus.OK);
    }

}
