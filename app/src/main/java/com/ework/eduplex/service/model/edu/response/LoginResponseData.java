package com.ework.eduplex.service.model.edu.response;

import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.service.model.edu.Customer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kseta on 2/1/17.
 */

public class LoginResponseData {

    @SerializedName("authorization")
    private String authorization;

    @SerializedName("timelines")
    private ArrayList<Timeline> timelines;

    @SerializedName("customer")
    private Customer customer;

    public LoginResponseData(String authorization, ArrayList<Timeline> timelines, Customer customer) {
        this.authorization = authorization;
        this.timelines = timelines;
        this.customer = customer;
    }

    public LoginResponseData() {
        this.authorization = "";
        this.timelines = new ArrayList<Timeline>();
        this.customer = new Customer("");
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(ArrayList<Timeline> timelines) {
        this.timelines = timelines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
