package com.virtusa.online_grocery_store.exceptions;

public class SellerNotFoundException extends RuntimeException{

    public SellerNotFoundException(String message)
    {
        super(message);
    }
}
