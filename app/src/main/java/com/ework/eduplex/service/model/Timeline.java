package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/11/2016.
 */
public class Timeline {

    @SerializedName("date")
    private String date;

    @SerializedName("action")
    private String action;

    @SerializedName("news")
    private String news;

    @SerializedName("icon")
    private String icon;

    public Timeline(String date, String action, String news) {
        this.date = date;
        this.action = action;
        this.news = news;

        if (this.action == "PAY" || this.action == "TOPUP"){
            this.icon = this.action;
        }
    }

    public Timeline() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
