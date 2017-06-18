package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.SignupData;

/**
 * Created by eWork on 4/5/2016.
 */
public class SignupResponse {

    @SerializedName("meta")
    Meta meta;

    @SerializedName("data")
    SignupData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public SignupData getData() {
        return data;
    }

    public void setData(SignupData data) {
        this.data = data;
    }
}
