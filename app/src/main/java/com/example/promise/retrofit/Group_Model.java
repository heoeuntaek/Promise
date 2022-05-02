package com.example.promise.retrofit;

import com.google.gson.annotations.SerializedName;

public class Group_Model {
    @SerializedName("id")
    public Long id;

    @SerializedName("group_code")
    public String group_code;

    @SerializedName("group_name")
    public String group_name;

    @SerializedName("matched_schedule")
    public String matched_schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public String getMatched_schedule() {
        return matched_schedule;
    }

    public void setMatched_schedule(String matched_schedule) {
        this.matched_schedule = matched_schedule;
    }

    @Override
    public String toString() {
        return "Group_Model{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", group_code='" + group_code + '\'' +
                ", matched_schedule='" + matched_schedule + '\'' +
                '}';
    }
}
