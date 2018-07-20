package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;


/**
 * 选课（班级->课程->老师 一一对应）
 *
 * @author wyk
 */
public class Elective extends AutoIdEntity {

    private static final long serialVersionUID = 3632031711039967243L;

    /** 选课班级 */
    private Classes classes;

    /** 课程 */
    private Course course;

    /** 授课教师 */
    private Administrator teacher;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Administrator getTeacher() {
        return teacher;
    }

    public void setTeacher(Administrator teacher) {
        this.teacher = teacher;
    }
}
