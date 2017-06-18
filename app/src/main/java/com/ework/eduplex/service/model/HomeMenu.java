package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eWork on 8/25/2016.
 */
public class HomeMenu {

    @SerializedName("KPR")
    private String KPR;

    @SerializedName("JASA")
    private String JASA;

    @SerializedName("MODAL_USAHA")
    private String modalUsaha;

    @SerializedName("MULTIGUNA_ELEKTRONIK")
    private String multigunaElektronik;

    @SerializedName("MULTIGUNA_MOTOR")
    private String multigunaMotor;

    @SerializedName("MULTIGUNA_DAN_MODAL_USAHA")
    private String multigunaDanModalUsaha;

    public String getKPR() {
        return KPR;
    }

    public void setKPR(String KPR) {
        this.KPR = KPR;
    }

    public String getJASA() {
        return JASA;
    }

    public void setJASA(String JASA) {
        this.JASA = JASA;
    }

    public String getModalUsaha() {
        return modalUsaha;
    }

    public String getMultigunaElektronik() {
        return multigunaElektronik;
    }

    public void setMultigunaElektronik(String multigunaElektronik) {
        this.multigunaElektronik = multigunaElektronik;
    }

    public String getMultigunaMotor() {
        return multigunaMotor;
    }

    public void setMultigunaMotor(String multigunaMotor) {
        this.multigunaMotor = multigunaMotor;
    }

    public String getMultigunaDanModalUsaha() {
        return multigunaDanModalUsaha;
    }

    public void setMultigunaDanModalUsaha(String multigunaDanModalUsaha) {
        this.multigunaDanModalUsaha = multigunaDanModalUsaha;
    }

    public void setModalUsaha(String modalUsaha) {
        this.modalUsaha = modalUsaha;
    }
}
