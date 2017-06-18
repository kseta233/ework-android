package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/9/17.
 */

public class BaseEduMeta {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
