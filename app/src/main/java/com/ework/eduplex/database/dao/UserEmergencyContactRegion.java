package com.ework.eduplex.database.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by eWork on 6/30/2016.
 */

@DatabaseTable(tableName = "user_emergency_contact_region")
public class UserEmergencyContactRegion {
    @DatabaseField(columnName = "referalCode", id = true)
    private String referalCode;
    @DatabaseField(columnName = "provinceId")
    private String provinceId;
    @DatabaseField(columnName = "cityId")
    private String cityId;
    @DatabaseField(columnName = "kecamatanId")
    private String kecamatanId;
    @DatabaseField(columnName = "kelurahanId")
    private String kelurahanId;

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getKecamatanId() {
        return kecamatanId;
    }

    public void setKecamatanId(String kecamatanId) {
        this.kecamatanId = kecamatanId;
    }

    public String getKelurahanId() {
        return kelurahanId;
    }

    public void setKelurahanId(String kelurahanId) {
        this.kelurahanId = kelurahanId;
    }

    public UserEmergencyContactRegion() {

    }
}
