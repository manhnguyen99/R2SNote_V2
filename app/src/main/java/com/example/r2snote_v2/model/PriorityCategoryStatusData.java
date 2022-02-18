package com.example.r2snote_v2.model;

public class PriorityCategoryStatusData {
    private String tab;
    private String name;
    private String createdDate;
    private String email;

    public PriorityCategoryStatusData(String tab, String name, String createdDate, String email) {
        this.tab = tab;
        this.name = name;
        this.createdDate = createdDate;
        this.email = email;
    }

    public PriorityCategoryStatusData() {
    }

    public PriorityCategoryStatusData(String name, String createdDate, String email) {
        this.name = name;
        this.createdDate = createdDate;
        this.email = email;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
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