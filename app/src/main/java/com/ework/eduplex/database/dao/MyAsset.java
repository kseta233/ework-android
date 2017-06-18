package com.ework.eduplex.database.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by eWork on 4/20/2016.
 */
@DatabaseTable(tableName = "my_asset")
public class MyAsset {
    @DatabaseField(columnName = "assetId", generatedId = true)
    private int assetId;
    @DatabaseField(columnName = "tipeAset")
    private String tipeAset;
    @DatabaseField(columnName = "merkKendaraan")
    private String merkKendaraan;
    @DatabaseField(columnName = "tipeKendaraan")
    private String tipeKendaraan;
    @DatabaseField(columnName = "tahunKendaraan")
    private String tahunKendaraan;
    @DatabaseField(columnName = "tipelainnyaAset")
    private String lainnya;
    @DatabaseField(columnName = "atasNama")
    private String atasNama;
    @DatabaseField(columnName = "nilaiOTR")
    private String nilaiOTR;
    @DatabaseField(columnName = "jumlahKebutuhan")
    private String jumlahKebutuhan;
    @DatabaseField(columnName = "cicilanPerbulan")
    private String cicilanPerbulan;
    @DatabaseField(columnName = "hargaTotal")
    private String hargaTotal;
    @DatabaseField(columnName = "fotoKendaraanArrString")
    private String fotoKendaraanArrString;
    @DatabaseField(columnName = "fotoSuratArrString")
    private String fotoSuratArrString;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getTipeAset() {
        return tipeAset;
    }

    public void setTipeAset(String tipeAset) {
        this.tipeAset = tipeAset;
    }

    public String getMerkKendaraan() {
        return merkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        this.merkKendaraan = merkKendaraan;
    }

    public String getTipeKendaraan() {
        return tipeKendaraan;
    }

    public void setTipeKendaraan(String tipeKendaraan) {
        this.tipeKendaraan = tipeKendaraan;
    }

    public String getTahunKendaraan() {
        return tahunKendaraan;
    }

    public void setTahunKendaraan(String tahunKendaraan) {
        this.tahunKendaraan = tahunKendaraan;
    }

    public String getLainnya() {
        return lainnya;
    }

    public void setLainnya(String lainnya) {
        this.lainnya = lainnya;
    }

    public String getAtasNama() {
        return atasNama;
    }

    public void setAtasNama(String atasNama) {
        this.atasNama = atasNama;
    }

    public String getNilaiOTR() {
        return nilaiOTR;
    }

    public void setNilaiOTR(String nilaiOTR) {
        this.nilaiOTR = nilaiOTR;
    }

    public String getJumlahKebutuhan() {
        return jumlahKebutuhan;
    }

    public void setJumlahKebutuhan(String jumlahKebutuhan) {
        this.jumlahKebutuhan = jumlahKebutuhan;
    }

    public String getCicilanPerbulan() {
        return cicilanPerbulan;
    }

    public void setCicilanPerbulan(String cicilanPerbulan) {
        this.cicilanPerbulan = cicilanPerbulan;
    }

    public String getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(String hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    public String getFotoKendaraanArrString() {
        return fotoKendaraanArrString;
    }

    public void setFotoKendaraanArrString(String fotoKendaraanArrString) {
        this.fotoKendaraanArrString = fotoKendaraanArrString;
    }

    public String getFotoSuratArrString() {
        return fotoSuratArrString;
    }

    public void setFotoSuratArrString(String fotoSuratArrString) {
        this.fotoSuratArrString = fotoSuratArrString;
    }

    public MyAsset() {

    }

    public MyAsset(String tipeAset, String merkKendaraan, String tipeKendaraan, String tahunKendaraan, String lainnya, String atasNama, String nilaiOTR, String jumlahKebutuhan, String cicilanPerbulan, String hargaTotal, String fotoKendaraanArrString, String fotoSuratArrString) {

        this.tipeAset = tipeAset;
        this.merkKendaraan = merkKendaraan;
        this.tipeKendaraan = tipeKendaraan;
        this.tahunKendaraan = tahunKendaraan;
        this.lainnya = lainnya;
        this.atasNama = atasNama;
        this.nilaiOTR = nilaiOTR;
        this.jumlahKebutuhan = jumlahKebutuhan;
        this.cicilanPerbulan = cicilanPerbulan;
        this.hargaTotal = hargaTotal;
        this.fotoKendaraanArrString = fotoKendaraanArrString;
        this.fotoSuratArrString = fotoSuratArrString;
    }
}
