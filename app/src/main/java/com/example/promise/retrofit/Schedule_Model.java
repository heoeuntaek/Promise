package com.example.promise.retrofit;

import com.google.gson.annotations.SerializedName;

public class Schedule_Model {

    @SerializedName("schedule_name")
    private String schedule_name;


    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }
}
