package com.example.promise.retrofit;


import com.google.gson.annotations.SerializedName;

public class User_group_Model {

    @SerializedName("id")
    private Long id;

    @SerializedName("user")
    private User_Model user_model;

    @SerializedName("group_tbl")
    private Group_Model group_model;

    @SerializedName("shared_schedule")
    private String shared_schedule;


    @Override
    public String toString() {
        return "User_group_Model{" +
                "id=" + id +
                ", user_model=" + user_model +
                ", group_model=" + group_model +
                ", shared_schedule='" + shared_schedule + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getShared_schedule() {
        return shared_schedule;
    }

    public void setShared_schedule(String shared_schedule) {
        this.shared_schedule = shared_schedule;
    }
}
