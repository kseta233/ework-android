package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 8/29/2016.
 */
public class BaseResponse {

    @SerializedName("meta")
    private Meta meta;

//    @SerializedName("data")
//    private List<DescriptionType> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

//    public List<DescriptionType> getData() {
//        return data;
//    }
//
//    public void setData(List<DescriptionType> data) {
//        this.data = data;
//    }
}
