package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.ProductInstallment;

import java.util.List;

/**
 * Created by eWork on 6/9/2016.
 */
public class GetTenorAngsuranResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private List<ProductInstallment> data;

    public List<ProductInstallment> getData() {
        return data;
    }

    public void setData(List<ProductInstallment> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
