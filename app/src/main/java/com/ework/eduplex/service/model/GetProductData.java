package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 4/26/2016.
 */
public class GetProductData {

    @SerializedName("products")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
