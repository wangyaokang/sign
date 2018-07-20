package com.wyk.sign.model;

import java.util.List;

/**
 * 班级
 *
 * @author wyk
 *
 */
public class Classes extends BaseModel{

    private static final long serialVersionUID = -7200916837317882197L;

    /** 班级名称 */
    private String name;

    /** 班级管理者 */
    private Administrator admin;

    /** 授课老师 */
    private List<Administrator> teacherList;

    /** 班级成员 */
    private List<Student> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

    public List<Administrator> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Administrator> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
