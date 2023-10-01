package com.virtusa.online_grocery_store.exceptions;


public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
