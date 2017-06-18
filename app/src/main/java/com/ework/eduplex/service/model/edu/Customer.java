package com.ework.eduplex.service.model.edu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/1/17.
 */

public class Customer {
    private String id;
    private String ktp;
    private String hp;
    private String name;
    private String gender;
    private String email;
    Double balance;

    public Customer(String id, String ktp, String hp, String name, String gender, String email, Double balance) {
        this.id = id;
        this.ktp = ktp;
        this.hp = hp;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.balance = balance;
    }

    public Customer(String name) {
        this.id = "";
        this.ktp = "";
        this.hp = "";
        this.name = name;
        this.gender = "";
        this.email = "";
        this.balance = 0.0;
    }

    public Customer() {
        this.id = "-";
        this.ktp = "-";
        this.hp = "-";
        this.name = "Guest";
        this.gender = "-";
        this.email = "-";
        this.balance = 0.0;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
