package com.wyk.sign.model;

import java.util.Date;

public class Sign extends BaseModel{
    /** 创建者 */
    private String creator;
    /** 签到开始时间 */
    private Date startDate;
    /** 签到截止时间 */
    private Date stopDate;
    /** 签到地址 */
    private String signAddress;
    /** 签到对应班级 */
    private Classes classes;
    /** 签到对应课程 */
    private Course course;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
}
