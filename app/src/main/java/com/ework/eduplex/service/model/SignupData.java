package com.ework.eduplex.service.model;

import com.ework.eduplex.service.model.edu.Customer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 4/5/2016.
 */
public class SignupData {

    @SerializedName("profile")
    private Customer profile;

    @SerializedName("kp_tunai")
    private KPTunai kp_tunai;

    @SerializedName("timelines")
    private List<Timeline> timelines;

    @SerializedName("credentials")
    private Credentials credentials;

    @SerializedName("authorization")
    private String verification;

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public Customer getProfile() {
        return profile;
    }

    public void setProfile(Customer profile) {
        this.profile = profile;
    }

    public KPTunai getKp_tunai() {
        return kp_tunai;
    }

    public void setKp_tunai(KPTunai kp_tunai) {
        this.kp_tunai = kp_tunai;
    }

    public List<Timeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(List<Timeline> timelines) {
        this.timelines = timelines;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
