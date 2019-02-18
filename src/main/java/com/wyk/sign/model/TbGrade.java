package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;

/**
 * 成绩类
 *
 * @author wyk
 */
public class TbGrade extends AutoIdEntity {

    private static final long serialVersionUID = 1376047657826959L;

    /**
     * 授课信息
     */
    private TbElective tbElective;

    /**
     * 学生
     */
    private TbStudent tbStudent;

    /**
     * 考试成绩
     */
    private Integer testScore;

    /**
     * 平时成绩
     */
    private Integer courseScore;

    public TbElective getTbElective() {
        return tbElective;
    }

    public void setTbElective(TbElective tbElective) {
        this.tbElective = tbElective;
    }

    public TbStudent getTbStudent() {
        return tbStudent;
    }

    public void setTbStudent(TbStudent tbStudent) {
        this.tbStudent = tbStudent;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public Integer getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(Integer courseScore) {
        this.courseScore = courseScore;
    }
}
