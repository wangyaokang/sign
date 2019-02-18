package com.wyk.sign.model;


import java.util.Date;

/**
 * 具体签到（对应于学生）
 *
 * @author wyk
 */
public class TbSign extends BaseModel{

    private static final long serialVersionUID = 6536810760403103137L;

    /** 签到信息 */
    private TbSignInfo info;

    /** 签到学生 */
    private TbStudent tbStudent;

    /** 签到时间 */
    private Date signDate;

    /** 签到地点 */
    private String signAddress;

    public TbSignInfo getInfo() {
        return info;
    }

    public void setInfo(TbSignInfo info) {
        this.info = info;
    }

    public TbStudent getTbStudent() {
        return tbStudent;
    }

    public void setTbStudent(TbStudent tbStudent) {
        this.tbStudent = tbStudent;
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
