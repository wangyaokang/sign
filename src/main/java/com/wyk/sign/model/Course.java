package com.wyk.sign.model;

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
    private Administrator teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Administrator getTeacher() {
        return teacher;
    }

    public void setTeacher(Administrator teacher) {
        this.teacher = teacher;
    }
}
