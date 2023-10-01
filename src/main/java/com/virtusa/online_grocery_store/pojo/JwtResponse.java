package com.virtusa.online_grocery_store.pojo;

public class JwtResponse {

    private String JsonString;

    public JwtResponse(String jsonString) {
        JsonString = jsonString;
    }

    public JwtResponse() {
    }

    public String getJsonString() {
        return JsonString;
    }

    public void setJsonString(String jsonString) {
        JsonString = jsonString;
    }

}