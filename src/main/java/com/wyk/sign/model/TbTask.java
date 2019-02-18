package com.wyk.sign.model;

/**
 * 上交作业（学生）
 *
 * @author wyk
 */
public class TbTask extends BaseModel {

    private static final long serialVersionUID = 8178932499847268791L;

    /**
     * 作业信息
     */
    private TbTaskInfo info;

    /**
     * 学生
     */
    private TbStudent tbStudent;

    /**
     * 作业文件url
     *
     * <pre>可以为空</pre>
     */
    private String upFileUrl;

    /**
     * 描述
     */
    private String desc;

    /**
     * 评分
     */
    private Integer score;

    public TbTaskInfo getInfo() {
        return info;
    }

    public void setInfo(TbTaskInfo info) {
        this.info = info;
    }

    public TbStudent getTbStudent() {
        return tbStudent;
    }

    public void setTbStudent(TbStudent tbStudent) {
        this.tbStudent = tbStudent;
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
