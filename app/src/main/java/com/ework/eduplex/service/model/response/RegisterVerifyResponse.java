package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.RegisterVerifyData;

/**
 * Created by eWork on 7/12/2016.
 */
public class RegisterVerifyResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    RegisterVerifyData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public RegisterVerifyData getData() {
        return data;
    }

    public void setData(RegisterVerifyData data) {
        this.data = data;
    }
}
