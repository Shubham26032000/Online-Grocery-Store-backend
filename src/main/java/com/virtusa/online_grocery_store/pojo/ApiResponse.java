package com.virtusa.online_grocery_store.pojo;

public class ApiResponse {
    private boolean status;
    private String message;

    public ApiResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
