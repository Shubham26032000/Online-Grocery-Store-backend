package com.virtusa.online_grocery_store.pojo;


public class Delivered {
    boolean status;

    public Delivered(boolean status) {
        this.status = status;
    }

    public Delivered() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
