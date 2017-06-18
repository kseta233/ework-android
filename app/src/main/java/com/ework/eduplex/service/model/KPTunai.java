package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/11/2016.
 */
public class KPTunai {

    @SerializedName("amount")
    private String amount;

    @SerializedName("reference_code")
    private String reference_code;

    public KPTunai(String amount, String reference_code) {
        this.amount = amount;
        this.reference_code = reference_code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference_code() {
        return reference_code;
    }

    public void setReference_code(String reference_code) {
        this.reference_code = reference_code;
    }
}
