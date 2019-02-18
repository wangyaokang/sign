package com.wyk.sign.model;

import java.util.List;

/**
 * 管理人（用户中的一种）
 *
 *  <pre>
 *      userType = 1: 教师
 *      userType = 2: 辅导员
 *      userType = 3: 教务
 *  </pre>
 *
 * @author wyk
 *
 */
public class TbAdmin extends TbUser {

    private static final long serialVersionUID = -4859126390200613828L;

    /** 我的签到 */
    private List<TbSignInfo> signList;

    /** 任务（作业） */
    private List<TbTaskInfo> taskList;

    /** 所授课程 */
    private List<TbCourse> tbCourseList;

    /** 班级 */
    private List<TbClass> TbClassList;

    public List<TbSignInfo> getSignList() {
        return signList;
    }

    public void setSignList(List<TbSignInfo> signList) {
        this.signList = signList;
    }

    public List<TbTaskInfo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TbTaskInfo> taskList) {
        this.taskList = taskList;
    }

    public List<TbClass> getTbClassList() {
        return TbClassList;
    }

    public void setTbClassList(List<TbClass> tbClassList) {
        this.TbClassList = tbClassList;
    }

    public List<TbCourse> getTbCourseList() {
        return tbCourseList;
    }

    public void setTbCourseList(List<TbCourse> tbCourseList) {
        this.tbCourseList = tbCourseList;
    }
}
