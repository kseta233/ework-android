package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/25/2016.
 */
public class Type {

    @SerializedName("type_id")
    private String type_id;

    @SerializedName("type_name")
    private String type_name;

    @SerializedName("year")
    private String year;

    @SerializedName("otr")
    private String otr;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOtr() {
        return otr;
    }

    public void setOtr(String otr) {
        this.otr = otr;
    }
}
