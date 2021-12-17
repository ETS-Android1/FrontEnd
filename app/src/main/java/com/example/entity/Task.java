package com.example.entity;

import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String desc;

    @SerializedName("alarm_check")
    private String dateTime;

    @SerializedName("category")
    private String category;

    @SerializedName("push_notification")
    private String notification;

    private String callBackTime;

    @SerializedName("task_token")
    private String taskToken;

    @SerializedName("user_token")
    private String userToken;

    @SerializedName("status")
    private String status;


    private int id;



    public Task(String title, String desc, String dateTime, String category, String notification, String callBackTime, String taskToken, String status, int id) {
        this.title = title;
        this.desc = desc;
        this.dateTime = dateTime;
        this.category = category;
        this.notification = notification;
        this.callBackTime = callBackTime;
        this.taskToken = taskToken;
        this.status = status;
        this.id = id;
    }

    public Task(String title, String desc, String dateTime, String category, String notification, String userToken, String status) {
        this.title = title;
        this.desc = desc;
        this.dateTime = dateTime;
        this.category = category;
        this.notification = notification;
        this.userToken = userToken;
        this.status = status;
    }

    public Task(String title, String desc, String dateTime, String category, String notification, String taskToken) {
        this.title = title;
        this.desc = desc;
        this.dateTime = dateTime;
        this.category = category;
        this.notification = notification;
        this.taskToken = taskToken;
    }

    public Task(String title, String desc, String dateTime, String category, String notification, String callBackTime, String taskToken, String userToken, int id,String status) {
        this.title = title;
        this.desc = desc;
        this.dateTime = dateTime;
        this.category = category;
        this.notification = notification;
        this.callBackTime = callBackTime;
        this.taskToken = taskToken;
        this.userToken = userToken;
        this.id = id;
        this.status= status;
    }

    public Task(String title, String dateTime, String taskToken) {
        this.title = title;
        this.dateTime = dateTime;
        this.taskToken = taskToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(String callBackTime) {
        this.callBackTime = callBackTime;
    }

    public String getTaskToken() {
        return taskToken;
    }

    public void setTaskToken(String taskToken) {
        this.taskToken = taskToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
