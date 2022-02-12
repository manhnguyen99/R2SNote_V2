package com.example.r2snote_v2.model;

import java.io.Serializable;

public class Note implements Serializable {

    private long id;
    private String title;
    private String subTitle;
    private String dateTime;
    private String webLink;
    private String color;


    public Note(long id, String title, String subTitle, String dateTime, String webLink, String color) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.color = color;
    }

    public Note(String title, String subTitle, String dateTime, String webLink, String color) {
        this.title = title;
        this.subTitle = subTitle;
        this.dateTime = dateTime;
        this.webLink = webLink;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return title + ':' + dateTime;
    }
}
