package com.example.r2snote_v2.model;


import java.io.Serializable;

public class Info implements Serializable {
    private String FirstName;
    private String LastName;

    public Info(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    @Override
    public String toString() {
        return
                "FirstName: " + FirstName + '\'' +
                ", LastName: " + LastName + '\'' +
                '}';
    }
}
