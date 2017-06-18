package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/14/2016.
 */
public class UbahPINResponse {

    @SerializedName("meta")
    Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
