package com.ework.eduplex.service.model.edu.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/5/17.
 */

public class CheckTransResponseData {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
