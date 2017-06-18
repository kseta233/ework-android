package com.ework.eduplex.service.model.response;

import com.ework.eduplex.service.model.edu.response.LoginResponseData;
import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.LoginData;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/5/2016.
 */
public class LoginResponse {

    @SerializedName("meta")
    Meta meta;

    @SerializedName("data")
    LoginResponseData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public LoginResponseData getData() {
        return data;
    }

    public void setData(LoginResponseData data) {
        this.data = data;
    }
}
