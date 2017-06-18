package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 4/5/2016.
 */
public class LoginData {

    @SerializedName("refresh_token")
    private String refresh_token;

    @SerializedName("access_token")
    private String access_token;

    @SerializedName("profile")
    private SummaryProfile profile;

    @SerializedName("kp_tunai")
    private KPTunai kp_tunai;

    @SerializedName("timelines")
    private List<Timeline> timelines;

    @SerializedName("verification")
    private String verification;

    @SerializedName("user_id")
    private String user_id;


    public LoginData() {
    }

    public LoginData(String refresh_token, String access_token, SummaryProfile profile, KPTunai kp_tunai, List<Timeline> timelines) {
        this.refresh_token = refresh_token;
        this.access_token = access_token;
        this.profile = profile;
        this.kp_tunai = kp_tunai;
        this.timelines = timelines;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public SummaryProfile getProfile() {
        return profile;
    }

    public void setProfile(SummaryProfile profile) {
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
}
