package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/26/2016.
 */
public class ProductInstallment {

    @SerializedName("tenor")
    private String tenor;

    @SerializedName("installment")
    private String installment;

    @SerializedName("downpayment")
    private String downpayment;

    public String getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(String downpayment) {
        this.downpayment = downpayment;
    }

    public String getTenor() {
        return tenor;
    }

    public ProductInstallment(String tenor, String installment) {
        this.tenor = tenor;
        this.installment = installment;
    }

    public ProductInstallment() {

    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }
}