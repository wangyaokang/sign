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

    /** 班级创建者 */
    private Administrator creator;

    /** 班级成员 */
    private List<Student> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administrator getCreator() {
        return creator;
    }

    public void setCreator(Administrator creator) {
        this.creator = creator;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
