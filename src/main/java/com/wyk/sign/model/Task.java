package com.wyk.sign.model;

import java.util.Date;

public class Task extends BaseModel{
    /** 课程名 */
    private String coursename;
    /** 教师 (创建者）*/
    private String creator;
    /** 班级 */
    private Classes classes;
    /** 上传截止时间 */
    private Date deadlineTime;
    /** 备注 */
    private String remark;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
