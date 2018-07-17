package com.wyk.sign.model;

import java.util.Date;
import java.util.List;

/**
 * 签到信息
 *
 * @author wyk
 *
 */
public class SignInfo extends BaseModel{

    private static final long serialVersionUID = 3447772102234030500L;

    /** 创建者（老师） */
    private User teacher;

    /** 签到开始时间 */
    private Date startDate;

    /** 签到截止时间 */
    private Date stopDate;

    /** 签到地址 */
    private String signAddress;

    /** 对应班级 */
    private Classes classes;

    /** 对应课程 */
    private Course course;

    /** 对应签到情况 */
    private List<Sign> signList;

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Sign> getSignList() {
        return signList;
    }

    public void setSignList(List<Sign> signList) {
        this.signList = signList;
    }
}
