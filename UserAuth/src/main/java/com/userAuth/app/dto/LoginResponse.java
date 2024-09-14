package com.userAuth.app.dto;

import lombok.Data;
public class LoginResponse {
    private String token;
    private long expiresIn;

    // Setter for token
    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // Return the current object for chaining
    }

    // Setter for expiresIn
    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // Return the current object for chaining
    }

    // Getter methods (optional)
    public String getToken() {
        return token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
