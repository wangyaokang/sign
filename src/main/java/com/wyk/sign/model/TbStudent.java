package com.wyk.sign.model;

import java.util.List;

/**
 * 学生
 * <pre> userType = 3</pre>
 *
 * @author wyk
 *
 */
public class TbStudent extends TbUser {

    private static final long serialVersionUID = 4679329916433276386L;

    /** 学号 */
    private String sno;

    /** 所属班级 */
    private TbClass TbClass;

    /** 我的作业 */
    private List<TbTask> tbTaskList;

    /** 我的签到 */
    private List<TbSign> tbSignList;

    public TbClass getTbClass() {
        return TbClass;
    }

    public void setTbClass(TbClass tbClass) {
        this.TbClass = tbClass;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public List<TbTask> getTbTaskList() {
        return tbTaskList;
    }

    public void setTbTaskList(List<TbTask> tbTaskList) {
        this.tbTaskList = tbTaskList;
    }

    public List<TbSign> getTbSignList() {
        return tbSignList;
    }

    public void setTbSignList(List<TbSign> tbSignList) {
        this.tbSignList = tbSignList;
    }
}
