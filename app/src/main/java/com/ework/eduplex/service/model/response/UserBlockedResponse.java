package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 7/29/2016.
 */
public class UserBlockedResponse {

    @SerializedName("meta")
    private Meta meta;

    public UserBlockedResponse(Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {

        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
