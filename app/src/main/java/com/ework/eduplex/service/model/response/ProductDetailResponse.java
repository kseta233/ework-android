package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;
import com.ework.eduplex.service.model.ProductDetailData;

/**
 * Created by eWork on 4/26/2016.
 */
public class ProductDetailResponse {

    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private ProductDetailData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ProductDetailData getData() {
        return data;
    }

    public void setData(ProductDetailData data) {
        this.data = data;
    }
}
