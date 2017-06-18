package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 4/18/2016.
 */
public class DescriptionType {

    @SerializedName("type_name")
    private String type_name;

    @SerializedName("data")
    private List<TypeDescData> data;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<TypeDescData> getData() {
        return data;
    }

    public void setData(List<TypeDescData> data) {
        this.data = data;
    }
}
