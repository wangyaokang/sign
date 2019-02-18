package com.wyk.sign.model;

import java.util.List;

/**
 * 班级
 *
 * @author wyk
 *
 */
public class TbClass extends BaseModel{

    private static final long serialVersionUID = -7200916837317882197L;

    /** 班级名称 */
    private String name;

    /** 班级管理者 */
    private TbAdmin admin;

    /** 授课老师 */
    private List<TbAdmin> teacherList;

    /** 班级成员 */
    private List<TbStudent> tbStudentList;

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

    public List<TbAdmin> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TbAdmin> teacherList) {
        this.teacherList = teacherList;
    }

    public List<TbStudent> getTbStudentList() {
        return tbStudentList;
    }

    public void setTbStudentList(List<TbStudent> tbStudentList) {
        this.tbStudentList = tbStudentList;
    }
}
