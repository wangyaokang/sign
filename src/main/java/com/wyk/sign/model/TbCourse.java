package com.wyk.sign.model;

import java.util.List;

/**
 * 课程
 *
 * @author wyk
 */
public class TbCourse extends BaseModel{

    private static final long serialVersionUID = -7522836192412894623L;

    /** 课程名 */
    private String name;

    /** 授课老师 */
    private List<TbAdmin> teacherList;

    /** 选课班级 */
    private List<TbClass> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TbAdmin> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TbAdmin> teacherList) {
        this.teacherList = teacherList;
    }

    public List<TbClass> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<TbClass> studentList) {
        this.studentList = studentList;
    }
}
