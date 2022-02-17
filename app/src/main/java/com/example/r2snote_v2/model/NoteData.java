package com.example.r2snote_v2.model;

import java.io.Serializable;

public class NoteData implements Serializable {
    private String name;
    private String category;
    private String priority;
    private String status;
    private String planeDate;
    private String createDate;
    private String email;

    public NoteData(String name, String category, String priority, String status, String planeDate, String createDate, String email) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.planeDate = planeDate;
        this.createDate = createDate;
        this.email = email;
    }

    public NoteData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlaneDate() {
        return planeDate;
    }

    public void setPlaneDate(String planeDate) {
        this.planeDate = planeDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
