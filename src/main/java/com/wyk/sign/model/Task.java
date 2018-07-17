package com.wyk.sign.model;

/**
 * 上交作业（学生）
 *
 * @author wyk
 *
 */
public class Task extends BaseModel{

    private static final long serialVersionUID = 8178932499847268791L;

    /** 作业信息 */
    private TaskInfo taskInfo;

    /** 学生 */
    private User student;

    /**
     * 作业文件url
     *
     * <pre>可以为空</pre>
     * */
    private String upFileUrl;

    /** 描述 */
    private String desc;

    /** 评分 */
    private Integer score;

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getUpFileUrl() {
        return upFileUrl;
    }

    public void setUpFileUrl(String upFileUrl) {
        this.upFileUrl = upFileUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
