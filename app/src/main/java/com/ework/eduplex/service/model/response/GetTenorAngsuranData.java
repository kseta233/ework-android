package com.ework.eduplex.service.model.response;
import com.google.gson.annotations.SerializedName;
import com.ework.eduplex.service.model.ProductInstallment;

import java.util.List;

/**
 * Created by eWork on 6/9/2016.
 */
public class GetTenorAngsuranData {

    @SerializedName("product_installments")
    private List<ProductInstallment> product_installments;

    public List<ProductInstallment> getProduct_installments() {
        return product_installments;
    }

    public void setProduct_installments(List<ProductInstallment> product_installments) {
        this.product_installments = product_installments;
    }
}
