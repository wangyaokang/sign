package com.wyk.sign.model;

import java.util.Date;
import java.util.List;

/**
 * 签到信息
 *
 * @author wyk
 *
 */
public class TbSignInfo extends BaseModel{

    private static final long serialVersionUID = 3447772102234030500L;


    /**
     * 签到名称
     *
     * <pre>默认是课程名</pre>
     */
    private String name;

    /** 对应班级 */
    private TbClass TbClass;

    /** 管理者 */
    private TbAdmin admin;

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

    /** 备注 */
    private String remark;

    /** 对应签到情况 */
    private List<TbSign> tbSignList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TbAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(TbAdmin admin) {
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

    public TbClass getTbClass() {
        return TbClass;
    }

    public void setTbClass(TbClass tbClass) {
        this.TbClass = tbClass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<TbSign> getTbSignList() {
        return tbSignList;
    }

    public void setTbSignList(List<TbSign> tbSignList) {
        this.tbSignList = tbSignList;
    }
}
