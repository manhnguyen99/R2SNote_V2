package com.example.r2snote_v2.model;

import java.util.List;

public class Priority {
    private int status;
    private List<List<String>> data;

    public Priority(int status, List<List<String>> data) {
        this.status = status;
        this.data = data;
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
