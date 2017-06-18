package com.ework.eduplex.service.model;

import com.ework.eduplex.utils.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/26/2016.
 */
public class Category {

    @SerializedName("category_id")
    private String category_id;

    @SerializedName("category_name")
    private String category_name;

    @SerializedName("category_parent_id")
    private String category_parent_id;

    @SerializedName("category_parent_name")
    private String category_parent_name;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    public Category(String category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_parent_id = Constant.CategoryID.BARANG_CATEGORY_ID;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_parent_id() {
        return category_parent_id;
    }

    public void setCategory_parent_id(String category_parent_id) {
        this.category_parent_id = category_parent_id;
    }

    public String getCategory_parent_name() {
        return category_parent_name;
    }

    public void setCategory_parent_name(String category_parent_name) {
        this.category_parent_name = category_parent_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
