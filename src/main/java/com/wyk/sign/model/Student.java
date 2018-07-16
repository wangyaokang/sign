package com.wyk.sign.model;

import java.util.List;

public class Student {
    /** 用户微信id */
    private String wxId;
    /** 专业 */
    private String professional;
    /** 所属班级名 */
    private String className;
    /** 学号 */
    private String stduentno;
    /** 我的课程 */
    private List<Task> courses;
    /** 我的考勤 */
    private List<Sign> signs;

    public String getWxId() {
        return wxId;
    }

    public Student setWxId(String wxId) {
        this.wxId = wxId;
        return this;
    }

    public String getProfessional() {
        return professional;
    }

    public Student setProfessional(String professional) {
        this.professional = professional;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Student setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getStduentno() {
        return stduentno;
    }

    public Student setStduentno(String stduentno) {
        this.stduentno = stduentno;
        return this;
    }

    public List<Sign> getSigns() {
        return signs;
    }

    public Student setSigns(List<Sign> signs) {
        this.signs = signs;
        return this;
    }
}
