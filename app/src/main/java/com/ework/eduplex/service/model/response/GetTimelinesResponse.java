package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.GetTimelinesData;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/18/2016.
 */
public class GetTimelinesResponse {

    @SerializedName("meta")
    Meta meta;

    @SerializedName("data")
    GetTimelinesData data;

    public GetTimelinesResponse() {
    }

    public GetTimelinesData getData() {
        return data;
    }

    public void setData(GetTimelinesData data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
