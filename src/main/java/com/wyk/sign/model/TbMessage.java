package com.wyk.sign.model;

public class TbMessage extends BaseModel{

    private static final long serialVersionUID = -2500187394698101974L;

    /** 主题 */
    private String topic;

    /** 主题图片 */
    private String imageUrl;

    /** 上传附件文件URL */
    private String uploadFileUrl;

    /** 内容 */
    private String content;

    /** 发布人 */
    private TbAdmin creator;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUploadFileUrl() {
        return uploadFileUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        this.uploadFileUrl = uploadFileUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TbAdmin getCreator() {
        return creator;
    }

    public void setCreator(TbAdmin creator) {
        this.creator = creator;
    }
}
