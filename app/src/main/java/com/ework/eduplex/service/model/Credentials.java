package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 7/20/2016.
 */
public class Credentials {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public Credentials() {
    }

    public Credentials(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
