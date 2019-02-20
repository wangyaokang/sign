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
public class TbTaskInfo extends BaseModel{

    private static final long serialVersionUID = -5646896236224855482L;

    /** 课程名 */
    private TbCourse tbCourse;

    /** 教师 (创建者）*/
    private TbAdmin admin;

    /** 班级 */
    private TbClass TbClass;

    /** 上传截止时间 */
    private Date deadlineTime;

    /** 作业内容 */
    private String content;

    /** 题目 */
    private String title;

    /** 作业情况 */
    private List<Task> taskList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TbCourse getTbCourse() {
        return tbCourse;
    }

    public void setTbCourse(TbCourse tbCourse) {
        this.tbCourse = tbCourse;
    }

    public TbAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(TbAdmin admin) {
        this.admin = admin;
    }

    public TbClass getTbClass() {
        return TbClass;
    }

    public void setTbClass(TbClass tbClass) {
        this.TbClass = tbClass;
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
