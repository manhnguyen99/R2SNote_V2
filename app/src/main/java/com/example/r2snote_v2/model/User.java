package com.example.r2snote_v2.model;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String email;
    private String passWord;
    private String firstName;
    private String lastName;
    private int status;
    private int error;
    private Info info;


    public User(String email, String passWord, int status, int error, Info info) {
        this.email = email;
        this.passWord = passWord;
        this.status = status;
        this.error = error;
        this.info = info;
    }


    public User() {
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", passWord='" + passWord + '\'' +
                ", status=" + status +
                ", error=" + error +
                ", info=" + info +
                '}';
    }
}



