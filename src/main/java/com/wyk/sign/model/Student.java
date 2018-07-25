package com.wyk.sign.model;

import java.util.List;

/**
 * 学生（用户其中一种类型）
 *
 * <pre> userType = 3</pre>
 * @author wyk
 *
 */
public class Student extends User{

    private static final long serialVersionUID = 4679329916433276386L;

    /** 所属班级 */
    private Classes classes;

    /** 学号 */
    private String sno;

    /** 我的作业 */
    private List<Task> taskList;

    /** 我的签到 */
    private List<Sign> signList;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Sign> getSignList() {
        return signList;
    }

    public void setSignList(List<Sign> signList) {
        this.signList = signList;
    }
}
