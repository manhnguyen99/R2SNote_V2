package com.example.r2snote_v2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Note {

    private long status;
    private long error;
    private List<List<String>> data;


    public Note(long status, long error, List<List<String>> data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getError() {
        return error;
    }

    public void setError(long error) {
        this.error = error;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
