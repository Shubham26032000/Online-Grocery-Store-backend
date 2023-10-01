package com.virtusa.online_grocery_store.exceptions;

public class OrderCannotDelete extends RuntimeException{

    public OrderCannotDelete(String message)
    {
        super(message);
    }
}
