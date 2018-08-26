package com.wyk.sign.model;

import javafx.concurrent.Task;

import java.util.Date;
import java.util.List;

/**
 * 作业信息
 *
 * @author wyk
 *
 */
public class TaskInfo extends BaseModel{

    private static final long serialVersionUID = -5646896236224855482L;

    /** 课程名 */
    private Course course;

    /** 教师 (创建者）*/
    private Administrator admin;

    /** 班级 */
    private Classes classes;

    /** 上传截止时间 */
    private Date deadlineTime;

    /** 作业内容 */
    private String content;

    /** 题目 */
    private String title;

    private List<Task> taskList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
