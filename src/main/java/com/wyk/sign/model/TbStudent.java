package com.wyk.sign.model;

import java.util.List;

/**
 * 学生
 *
 * @author wyk
 *
 */
public class TbStudent extends TbUser {

    private static final long serialVersionUID = 4679329916433276386L;

    /** 学号 */
    private String sno;

    /** 组别 */
    private TbGroup tbGroup;

    /** 课程 */
    private List<TbCourse> tbCourseList;

    /** 所属班级 */
    private List<TbClass> tbClassList;

    /** 我的作业 */
    private List<TbTask> tbTaskList;

    /** 我的签到 */
    private List<TbSign> tbSignList;

    public TbGroup getTbGroup() {
        return tbGroup;
    }

    public void setTbGroup(TbGroup tbGroup) {
        this.tbGroup = tbGroup;
    }

    public List<TbCourse> getTbCourseList() {
        return tbCourseList;
    }

    public void setTbCourseList(List<TbCourse> tbCourseList) {
        this.tbCourseList = tbCourseList;
    }

    public List<TbClass> getTbClassList() {
        return tbClassList;
    }

    public void setTbClassList(List<TbClass> tbClassList) {
        this.tbClassList = tbClassList;
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
