package com.example.r2snote_v2.model;

import java.util.List;

public class Dashboard {

    private int status;
    private List<List<String>> data;
    private String email;

    public Dashboard(int status, List<List<String>> data) {
        this.status = status;
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
