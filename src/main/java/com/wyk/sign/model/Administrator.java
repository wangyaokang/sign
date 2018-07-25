package com.wyk.sign.model;

import java.util.List;

/**
 * 管理人（用户中的一种）
 *
 *  <pre>
 *      userType = 1: 教师
 *      userType = 2: 辅导员
 *  </pre>
 *
 * @author wyk
 *
 */
public class Administrator extends User{

    private static final long serialVersionUID = -4859126390200613828L;

    /** 我的签到 */
    private List<SignInfo> signList;

    /** 任务（作业） */
    private List<TaskInfo> taskList;

    /** 所授课程 */
    private List<Course> courseList;

    /** 班级 */
    private List<Classes> classesList;

    public List<SignInfo> getSignList() {
        return signList;
    }

    public void setSignList(List<SignInfo> signList) {
        this.signList = signList;
    }

    public List<TaskInfo> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskInfo> taskList) {
        this.taskList = taskList;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
