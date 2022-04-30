package com.example.promise.retrofit;

import com.google.gson.annotations.SerializedName;

public class Schedule_Model {

    @SerializedName("id")
    private Long id;

    @SerializedName("schedule_name")
    private String schedule_name;




    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    @Override
    public String toString() {
        return "Schedule_Model{" +
                "id=" + id +
                ", schedule_name='" + schedule_name + '\'' +
                '}';
    }
}
