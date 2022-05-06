package com.example.promise.retrofit;

import com.google.gson.annotations.SerializedName;

public class Schedule_Model {

    @SerializedName("schedule_id")
    private Long id;

    @SerializedName("schedule_data")
    public String schedule_data;

    @SerializedName("schedule_name")
    private String schedule_name;

    @SerializedName("user")
    private User_Model user_model;

    @SerializedName("group_tbl")
    private Group_Model group_model;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchedule_data() {
        return schedule_data;
    }

    public void setSchedule_data(String schedule_data) {
        this.schedule_data = schedule_data;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public User_Model getUser_model() {
        return user_model;
    }

    public void setUser_model(User_Model user_model) {
        this.user_model = user_model;
    }

    public Group_Model getGroup_model() {
        return group_model;
    }

    public void setGroup_model(Group_Model group_model) {
        this.group_model = group_model;
    }

    @Override
    public String toString() {
        return "Schedule_Model{" +
                "id=" + id +
                ", schedule_data='" + schedule_data + '\'' +
                ", schedule_name='" + schedule_name + '\'' +
                ", user_model=" + user_model +
                ", group_model=" + group_model +
                '}';
    }

}