package com.virtusa.online_grocery_store.exceptions;

public class EmptyCartException extends RuntimeException{

    public EmptyCartException(String message)
    {
        super(message);
    }
}
