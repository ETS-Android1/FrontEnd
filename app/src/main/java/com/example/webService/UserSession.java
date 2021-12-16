package com.example.webService;

import com.example.entity.User;

public class UserSession {

    private User user;
    private String token;
    private String username;
    private String expiry;

    public UserSession(User user, String token) {
        this.user = user;
        this.token = token;
    }
    public UserSession(User user) {
        this.user = user;
    }

    public UserSession(String token, String username, String expiry) {
        this.token = token;
        this.expiry = expiry;
    }



    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
