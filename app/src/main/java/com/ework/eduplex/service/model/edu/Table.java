package com.ework.eduplex.service.model.edu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kseta on 2/5/17.
 */

public class Table {
    @SerializedName("id")
    String id;

    @SerializedName("lokasi")
    String lokasi;

    @SerializedName("map_name")
    String mapName;

    @SerializedName("capacity")
    int capacity;

    @SerializedName("avaibility")
    int avaibility;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int isAvaibility() {
        return avaibility;
    }

    public void setAvaibility(int avaibility) {
        this.avaibility = avaibility;
    }
}
