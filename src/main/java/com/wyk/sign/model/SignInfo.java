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

    /** 管理者 */
    private Administrator admin;

    /** 签到开始时间 */
    private Date startDate;

    /** 签到截止时间 */
    private Date stopDate;

    /** 签到地址 */
    private String address;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 对应班级 */
    private Classes classes;

    /** 对应课程 */
    private Course course;

    /** 备注 */
    private String remark;

    /** 对应签到情况 */
    private List<Sign> signList;

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
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

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Sign> getSignList() {
        return signList;
    }

    public void setSignList(List<Sign> signList) {
        this.signList = signList;
    }
}
