package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 6/6/2016.
 */
public class HomeKPR {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("product_flow_type")
    private String product_flow_type;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_flow_type() {
        return product_flow_type;
    }

    public void setProduct_flow_type(String product_flow_type) {
        this.product_flow_type = product_flow_type;
    }
}
