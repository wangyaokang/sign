package com.wyk.sign.model;

import java.util.List;

/**
 * 课程
 */
public class Course extends BaseModel{
    /** 课程名 */
    private String coursename;
    /** 课程对应班级 */
    private List<Classes> classesList;

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }
}
