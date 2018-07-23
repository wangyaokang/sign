package com.wyk.sign.model;

import java.util.List;

/**
 * 课程
 *
 * @author wyk
 */
public class Course extends BaseModel{

    private static final long serialVersionUID = -7522836192412894623L;

    /** 课程名 */
    private String name;

    /** 授课老师 */
    private List<Administrator> teacherList;

    /** 选课班级 */
    private List<Classes> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Administrator> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Administrator> teacherList) {
        this.teacherList = teacherList;
    }

    public List<Classes> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Classes> studentList) {
        this.studentList = studentList;
    }
}
