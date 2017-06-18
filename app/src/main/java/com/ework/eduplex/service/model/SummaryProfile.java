package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 4/11/2016.
 */
public class SummaryProfile {

    @SerializedName("UID")
    private String UID;

    @SerializedName("name")
    private String name;

    @SerializedName("referal_code")
    private String referal_code;
//
//    @SerializedName("branch_id")
//    private String branch_id;
//
//    @SerializedName("birthdate")
//    private String birthdate;
//
//    @SerializedName("no_ktp")
//    private String no_ktp;


    public SummaryProfile() {
    }

    public SummaryProfile(String UID, String name, String referal_code) {
        this.UID = UID;
        this.name = name;
        this.referal_code = referal_code;
//        this.branch_id = branch_id;
    }

//    public SummaryProfile(String UID, String name, String referal_code) {
//        this.UID = UID;
//        this.name = name;
//        this.referal_code = referal_code;
////        this.branch_id = branch_id;
////        this.birthdate = birthdate;
////        this.no_ktp = no_ktp;
//    }

//    public String getBirthdate() {
//
//        return birthdate;
//    }

//    public void setBirthdate(String birthdate) {
//        this.birthdate = birthdate;
//    }
//
//    public String getNo_ktp() {
//        return no_ktp;
//    }
//
//    public void setNo_ktp(String no_ktp) {
//        this.no_ktp = no_ktp;
//    }

    public String getUID() {

        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public void setReferal_code(String referal_code) {
        this.referal_code = referal_code;
    }

//    public String getBranch_id() {
//        return branch_id;
//    }
//
//    public void setBranch_id(String branch_id) {
//        this.branch_id = branch_id;
//    }
}
