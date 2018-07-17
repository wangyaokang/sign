package com.wyk.sign.model;

import java.util.Date;

/**
 * 具体签到（对应于学生）
 *
 * @author wyk
 */
public class Sign extends BaseModel{

    private static final long serialVersionUID = 6536810760403103137L;

    /** 签到信息 */
    private SignInfo signInfo;

    /** 签到学生 */
    private User student;

    /** 签到时间 */
    private Date signDate;

    /** 签到地点 */
    private String signAddress;

    public SignInfo getSignInfo() {
        return signInfo;
    }

    public void setSignInfo(SignInfo signInfo) {
        this.signInfo = signInfo;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }
}
