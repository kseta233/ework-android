package com.ework.eduplex.service.model;

/**
 * Created by kseta on 2/8/17.
 */

public class Promo {
    String product_internal_link;
    String image_url;
    String promo_product_internal_link;
    String promo_url;

    public String getProduct_internal_link() {
        return product_internal_link;
    }

    public void setProduct_internal_link(String product_internal_link) {
        this.product_internal_link = product_internal_link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPromo_product_internal_link() {
        return promo_product_internal_link;
    }

    public void setPromo_product_internal_link(String promo_product_internal_link) {
        this.promo_product_internal_link = promo_product_internal_link;
    }

    public String getPromo_url() {
        return promo_url;
    }

    public void setPromo_url(String promo_url) {
        this.promo_url = promo_url;
    }
}
