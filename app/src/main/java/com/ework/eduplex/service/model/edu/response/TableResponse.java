package com.ework.eduplex.service.model.edu.response;

import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.SignupData;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/5/17.
 */

public class TableResponse {

    @SerializedName("meta")
    Meta meta;

    @SerializedName("data")
    TableResponseData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public TableResponseData getData() {
        return data;
    }

    public void setData(TableResponseData data) {
        this.data = data;
    }
}
