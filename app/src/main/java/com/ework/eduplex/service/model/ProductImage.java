package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/26/2016.
 */
public class ProductImage {

    @SerializedName("product_image_thumb")
    private String product_image_thumb;

    @SerializedName("product_image_large")
    private String product_image_large;

    public String getProduct_image_thumb() {
        return product_image_thumb;
    }

    public void setProduct_image_thumb(String product_image_thumb) {
        this.product_image_thumb = product_image_thumb;
    }

    public String getProduct_image_large() {
        return product_image_large;
    }

    public void setProduct_image_large(String product_image_large) {
        this.product_image_large = product_image_large;
    }
}
