package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 5/16/2016.
 */
public class GetHomeData {

    @SerializedName("promos")
    private List<Promo> promos;

    @SerializedName("categories")
    private List<Category> categories;

    @SerializedName("hot_products")
    private List<Product> hot_products;

    @SerializedName("kpr")
    private List<HomeKPR> kpr;

    @SerializedName("menus")
    private HomeMenu menus;

    public GetHomeData() {
    }

    public List<Promo> getPromos() {
        return promos;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Product> getHot_products() {
        return hot_products;
    }

    public void setHot_products(List<Product> hot_products) {
        this.hot_products = hot_products;
    }

    public List<HomeKPR> getKpr() {
        return kpr;
    }

    public void setKpr(List<HomeKPR> kpr) {
        this.kpr = kpr;
    }

    public HomeMenu getMenus() {
        return menus;
    }

    public void setMenus(HomeMenu menus) {
        this.menus = menus;
    }
}
