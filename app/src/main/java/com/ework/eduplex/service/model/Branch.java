package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/18/2016.
 */
public class Branch {

    @SerializedName("branch_id")
    private String branch_id;

    @SerializedName("branch_name")
    private String branch_name;

    @SerializedName("branch_address")
    private String branch_address;

    @SerializedName("branch_lat")
    private String branch_lat;

    @SerializedName("branch_lng")
    private String branch_lng;

    @SerializedName("branch_kpr_service")
    private String branch_kpr_service;

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_address() {
        return branch_address;
    }

    public void setBranch_address(String branch_address) {
        this.branch_address = branch_address;
    }

    public String getBranch_lat() {
        return branch_lat;
    }

    public void setBranch_lat(String branch_lat) {
        this.branch_lat = branch_lat;
    }

    public String getBranch_lng() {
        return branch_lng;
    }

    public void setBranch_lng(String branch_lng) {
        this.branch_lng = branch_lng;
    }

    public String getBranch_kpr_service() {
        return branch_kpr_service;
    }

    public void setBranch_kpr_service(String branch_kpr_service) {
        this.branch_kpr_service = branch_kpr_service;
    }
}
