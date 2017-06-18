package com.ework.eduplex.service.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eWork on 4/26/2016.
 */
public class ProductDetailData {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("product_flow_type")
    private String product_flow_type;

    @SerializedName("product_short_description")
    private String product_short_description;

    @SerializedName("product_price")
    private String product_price;

    @SerializedName("product_category_id")
    private String product_category_id;

    @SerializedName("product_images")
    private List<String> product_images;

    @SerializedName("product_type")
    private String product_type;

    @SerializedName("product_level")
    private String product_level;

    @SerializedName("product_short_terms")
    private String product_short_terms;

    @SerializedName("product_description")
    private String product_description;

    @SerializedName("product_installment")
    private List<ProductInstallment> product_installment;

    @SerializedName("branch_id")
    private String branch_id;

    @SerializedName("in_branch")
    private int in_branch;

    @SerializedName("product_branch_id")
    private String product_branch_id;

    public String getProduct_branch_id() {
        return product_branch_id;
    }

    public ProductDetailData(Product product) {
        Log.d("MYTAG", "ProductDetailData: "+product.toString());
        this.product_id = product.getProduct_id();
        this.product_name = product.getProduct_name();
        this.product_flow_type = "2";
        this.product_short_description = product.getProduct_short_description();
        this.product_price = product.getProduct_price();
        this.product_category_id = product.getProduct_category_id();
        this.product_images = product.getProduct_images();
        this.product_description = product.getProduct_short_description();
        this.product_type = "this is product type";
        this.product_level = "this is product level";
        this.product_short_terms = "this is product short terms";
        this.branch_id = "2";
        this.in_branch = 2;
        this.product_installment = new ArrayList<>();
        this.product_branch_id = "2";
    }

    public void setProduct_branch_id(String product_branch_id) {
        this.product_branch_id = product_branch_id;
    }

    public int getIn_branch() {
        return in_branch;
    }

    public void setIn_branch(int in_branch) {
        this.in_branch = in_branch;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getProduct_flow_type() {
        return product_flow_type;
    }

    public void setProduct_flow_type(String product_flow_type) {
        this.product_flow_type = product_flow_type;
    }

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

    public String getFlow_type() {
        return product_flow_type;
    }

    public void setFlow_type(String product_flow_type) {
        this.product_flow_type = product_flow_type;
    }

    public String getProduct_short_description() {
        return product_short_description;
    }

    public void setProduct_short_description(String product_short_description) {
        this.product_short_description = product_short_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_level() {
        return product_level;
    }

    public void setProduct_level(String product_level) {
        this.product_level = product_level;
    }

    public String getProduct_short_terms() {
        return product_short_terms;
    }

    public void setProduct_short_terms(String product_short_terms) {
        this.product_short_terms = product_short_terms;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public List<ProductInstallment> getProduct_installment() {
        return product_installment;
    }

    public void setProduct_installment(List<ProductInstallment> product_installment) {
        this.product_installment = product_installment;
    }
}
