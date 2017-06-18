package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/9/17.
 */

public class BaseEduResponse {

    @SerializedName("meta")
    private BaseEduMeta meta;

    @SerializedName("data")
    private String data;

    public BaseEduMeta getMeta() {
        return meta;
    }

    public void setMeta(BaseEduMeta meta) {
        this.meta = meta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
