package com.wyk.sign.model;

public class Message extends BaseModel{

    private static final long serialVersionUID = -2500187394698101974L;

    /** 主题 */
    private String topic;

    /** 主题图片 */
    private String topicImageUrl;

    /** 上传附件文件URL */
    private String uploadFileUrl;

    /** 内容 */
    private String content;

    /** 发布人 */
    private Administrator admin;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicImageUrl() {
        return topicImageUrl;
    }

    public void setTopicImageUrl(String topicImageUrl) {
        this.topicImageUrl = topicImageUrl;
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

    public Administrator getAdmin() {
        return admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }
}
