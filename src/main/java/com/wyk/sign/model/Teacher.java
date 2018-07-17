package com.wyk.sign.model;

import java.util.List;

public class Teacher extends User{
    /** 我的签到 */
    private List<Sign> signs;
    /** 我的任务 */
    private List<Task> tasks;

    public String getWxId() {
        return wxId;
    }

    public List<Sign> getSigns() {
        return signs;
    }

    public void setSigns(List<Sign> signs) {
        this.signs = signs;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
