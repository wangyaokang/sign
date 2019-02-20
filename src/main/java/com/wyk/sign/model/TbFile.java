package com.wyk.sign.model;

/**
 * 上传文件类
 *
 * @author wyk
 */
public class TbFile extends BaseModel{

    /** 文件名 */
    private String fileName;
    /** 文件路径 */
    private String fileUrl;
    /** 上传者 */
    private String creator;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
