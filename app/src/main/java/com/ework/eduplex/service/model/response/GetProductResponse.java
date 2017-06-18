package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.GetProductData;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/26/2016.
 */
public class GetProductResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private GetProductData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GetProductData getData() {
        return data;
    }

    public void setData(GetProductData data) {
        this.data = data;
    }
}
