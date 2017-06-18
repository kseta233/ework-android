package com.ework.eduplex.service.model.edu.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/5/17.
 */

public class SecretResponseData {

    @SerializedName("secret")
    private String secret;

    @SerializedName("pair_id")
    private String pairId;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }
}
