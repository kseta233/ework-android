package com.ework.eduplex.service.model.response;

import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.Meta;

/**
 * Created by eWork on 4/13/2016.
 */
public class EditProfileResponse {

    @SerializedName("meta")
    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
