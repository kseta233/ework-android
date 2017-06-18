package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.GetHomeData;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 5/16/2016.
 */
public class GetHomeResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private GetHomeData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GetHomeData getData() {
        return data;
    }

    public void setData(GetHomeData data) {
        this.data = data;
    }
}
