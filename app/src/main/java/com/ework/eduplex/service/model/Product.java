package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eWork on 4/26/2016.
 */
public class Product implements Serializable{

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("product_short_description")
    private String product_short_description;

    @SerializedName("product_category_id")
    private String product_category_id;

    @SerializedName("product_price")
    private String product_price;

    @SerializedName("product_installment")
    private String product_installment;

    @SerializedName("product_images")
    private List<String> product_images;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_short_description() {
        return product_short_description;
    }

    public void setProduct_short_description(String product_short_description) {
        this.product_short_description = product_short_description;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_installment() {
        return product_installment;
    }

    public void setProduct_installment(String product_installment) {
        this.product_installment = product_installment;
    }

    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_short_description='" + product_short_description + '\'' +
                ", product_category_id='" + product_category_id + '\'' +
                ", product_price='" + product_price + '\'' +
                ", product_installment='" + product_installment + '\'' +
                ", product_images=" + product_images +
                '}';
    }
}
