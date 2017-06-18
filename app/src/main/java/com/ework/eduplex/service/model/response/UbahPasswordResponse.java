package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.UbahPasswordData;

/**
 * Created by eWork on 4/14/2016.
 */
public class UbahPasswordResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private UbahPasswordData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public UbahPasswordData getData() {
        return data;
    }

    public void setData(UbahPasswordData data) {
        this.data = data;
    }
}
