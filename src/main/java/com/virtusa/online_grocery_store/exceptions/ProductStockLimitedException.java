package com.virtusa.online_grocery_store.exceptions;

public class ProductStockLimitedException extends RuntimeException{

    public ProductStockLimitedException(String message)
    {
        super(message);
    }
}
