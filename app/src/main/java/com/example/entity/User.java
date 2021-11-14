package com.example.entity;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("password2")
    private String password2;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("token")
    private String CodeToken;

    @SerializedName("phone_number")
    private String phone_number;

    public User(String email) {
        this.email = email;
    }


    public User() {
    }

    public User(String password, String password2, String codeToken) {
        this.password = password;
        this.password2 = password2;
        CodeToken = codeToken;
    }

    public User(String username, String email, String password, String password2, String first_name, String last_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public User(String username, String email, String password, String password2, String first_name, String last_name , String phone_number) {
        this.phone_number=phone_number;
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getCodeToken() {
        return CodeToken;
    }

    public void setCodeToken(String codeToken) {
        CodeToken = codeToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
