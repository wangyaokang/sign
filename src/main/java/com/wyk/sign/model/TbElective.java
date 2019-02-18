package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;

import java.util.Date;


/**
 * 授课（班级->课程->老师 一一对应）
 *
 * @author wyk
 */
public class TbElective extends AutoIdEntity {

    private static final long serialVersionUID = 3632031711039967243L;

    /** 选课班级 */
    private TbClass TbClass;

    /** 课程 */
    private TbCourse tbCourse;

    /** 授课教师 */
    private TbAdmin admin;

    /** 平时分占比（%） */
    private Integer courseScoreRatio;

    /** 考试分占比（%） */
    private Integer testScoreRatio;

    /** 学期起始时间 */
    private Date termStartDate;

    /** 学期结束时间 */
    private Date termStopDate;

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermStopDate() {
        return termStopDate;
    }

    public void setTermStopDate(Date termStopDate) {
        this.termStopDate = termStopDate;
    }

    public TbClass getTbClass() {
        return TbClass;
    }

    public void setTbClass(TbClass tbClass) {
        this.TbClass = tbClass;
    }

    public TbCourse getTbCourse() {
        return tbCourse;
    }

    public void setTbCourse(TbCourse tbCourse) {
        this.tbCourse = tbCourse;
    }

    public TbAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(TbAdmin admin) {
        this.admin = admin;
    }

    public Integer getCourseScoreRatio() {
        return courseScoreRatio;
    }

    public void setCourseScoreRatio(Integer courseScoreRatio) {
        this.courseScoreRatio = courseScoreRatio;
    }

    public Integer getTestScoreRatio() {
        return testScoreRatio;
    }

    public void setTestScoreRatio(Integer testScoreRatio) {
        this.testScoreRatio = testScoreRatio;
    }
}
