package com.virtusa.online_grocery_store.exceptions;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message)
    {
        super(message);
    }
}
