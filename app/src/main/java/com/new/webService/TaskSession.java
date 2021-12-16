package com.example.webService;

import com.example.entity.Task;

public class TaskSession {
    private Task task;

    public TaskSession(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
