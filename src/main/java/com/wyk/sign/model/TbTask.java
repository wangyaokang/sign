package com.wyk.sign.model;

import java.util.List;

/**
 * 上交作业（学生）
 *
 * @author wyk
 */
public class TbTask extends BaseModel {

    private static final long serialVersionUID = 8178932499847268791L;

    /** 作业信息 */
    private TbTaskInfo info;

    /** 学生 */
    private TbStudent tbStudent;

    /**
     * 作业文件
     *
     * <pre>可以为空</pre>
     */
    private List<TbFile> tbFileList;

    /** 描述 */
    private String desc;

    /** 评分 */
    private Double score;

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

    public List<TbFile> getTbFileList() {
        return tbFileList;
    }

    public void setTbFileList(List<TbFile> tbFileList) {
        this.tbFileList = tbFileList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
