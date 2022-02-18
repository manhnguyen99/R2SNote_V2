package com.example.r2snote_v2.model;

public class PriorityData {
    private String name;
    private String createdDate;
    private String email;

    public PriorityData(String name, String createdDate, String email) {
        this.name = name;
        this.createdDate = createdDate;
        this.email = email;
    }

    public PriorityData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}