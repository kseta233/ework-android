package com.ework.eduplex.service.model.edu.response;

import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.service.model.edu.Customer;
import com.ework.eduplex.service.model.edu.Table;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by kseta on 2/5/17.
 */

public class TableResponseData {

    @SerializedName("table")
    private ArrayList<Table> tables;

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
