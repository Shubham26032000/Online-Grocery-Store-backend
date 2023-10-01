package com.virtusa.online_grocery_store.pojo;

public class Paid {
    boolean status;
    public Paid() {
    }

    public Paid(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
