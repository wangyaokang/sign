package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;

import java.util.Date;

/**
 * 成绩类
 *
 * @author wyk
 */
public class Grade extends AutoIdEntity {

    private static final long serialVersionUID = 1376047657826959L;

    /** 授课信息 */
    private Elective elective;

    /** 学生 */
    private Student student;

    /** 考试成绩 */
    private Double testScore;

    /** 平时成绩 */
    private Double courseScore;

    /** 学期开始日期 */
    private Date termStartDate;

    /** 学期结束日期 */
    private Date termStopDate;

    public Elective getElective() {
        return elective;
    }

    public void setElective(Elective elective) {
        this.elective = elective;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

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

    public double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public double getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Double courseScore) {
        this.courseScore = courseScore;
    }
}
