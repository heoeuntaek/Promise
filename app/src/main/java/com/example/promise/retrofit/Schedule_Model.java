package com.example.promise.retrofit;

import com.google.gson.annotations.SerializedName;

public class Schedule_Model {
    /*private String data[];
    private String name;

    public Schedule_Model(String name,String data[]){
        this.name=name;
        for(int i=1;i<data.length-1;i++){
            this.data[i]=data[i];
        }
    }

    public String getName(){
        return name;
    }



} */

    @SerializedName("id")
    private Long id;

    @SerializedName("schedule_name")
    private String schedule_name;


    @SerializedName("data")
    private String[] data=new String[120];

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String[] getSchedule_data() {
       return data;
    }

    public void setSchedule_data(String data[]) {
        for(int i=1;i<=data.length-1;i++){
            this.data[i]=data[i];
        }
    }



    @Override
    public String toString() {
        return "Schedule_Model{" +
                "id=" + id +
                ", schedule_name='" + schedule_name + '\'' +
                '}';
    }
}
