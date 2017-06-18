package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.GetCategoriesData;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/26/2016.
 */
public class GetCategoriesResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private GetCategoriesData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GetCategoriesData getData() {
        return data;
    }

    public void setData(GetCategoriesData data) {
        this.data = data;
    }
}
