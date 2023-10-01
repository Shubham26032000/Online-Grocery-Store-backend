package com.virtusa.online_grocery_store.exceptions;


public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message)
    {
        super(message);
    }
}
