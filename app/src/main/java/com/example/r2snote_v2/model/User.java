package com.example.r2snote_v2.model;

public class User {

    private String userId;
    private String email;
    private String passWord;
    private String firstName;
    private String lastName;
    private int status;
    private int error;

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

    public User(String email, String passWord) {
        this.email = email;
        this.passWord = passWord;
    }

    public User(String userId, String email, String passWord, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String userId, String email, String passWord) {
        this.userId = userId;
        this.email = email;
        this.passWord = passWord;
    }

    public User(String email, String passWord, String firstName, String lastName) {
        this.email = email;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
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
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", passWord='" + passWord + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", error=" + error +
                '}';
    }
}
