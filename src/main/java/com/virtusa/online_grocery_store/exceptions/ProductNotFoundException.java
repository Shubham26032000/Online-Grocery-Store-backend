package com.virtusa.online_grocery_store.exceptions;


public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
