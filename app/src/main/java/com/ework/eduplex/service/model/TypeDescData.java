package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/18/2016.
 */
public class TypeDescData {

    @SerializedName("type_id")
    private String type_id;

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
