package com.wyk.sign.model;

import java.util.List;

/**
 *
 * 学生（用户其中一种类型）
 *
 */
public class Student extends User{
    /** 专业 */
    private String professional;
    /** 所属班级名 */
    private String className;
    /** 学号 */
    private String stduentno;
    /** 我的作业 */
    private List<Task> tasks;
    /** 我的考勤 */
    private List<Sign> signs;

    public String getWxId() {
        return wxId;
    }

    public Student setWxId(String wxId) {
        this.wxId = wxId;
        return this;
    }

    public String getProfessional() {
        return professional;
    }

    public Student setProfessional(String professional) {
        this.professional = professional;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Student setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getStduentno() {
        return stduentno;
    }

    public Student setStduentno(String stduentno) {
        this.stduentno = stduentno;
        return this;
    }

    public List<Sign> getSigns() {
        return signs;
    }

    public Student setSigns(List<Sign> signs) {
        this.signs = signs;
        return this;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
