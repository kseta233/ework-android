package com.ework.eduplex.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eWork on 4/18/2016.
 */
public class GetTimelinesData {

    @SerializedName("timelines")
    List<Timeline> timelines;

    public List<Timeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(List<Timeline> timelines) {
        this.timelines = timelines;
    }
}
