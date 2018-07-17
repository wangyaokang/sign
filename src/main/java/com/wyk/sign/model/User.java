package com.wyk.sign.model;

/**
 *
 * 用户（教师或学生）
 *
 */
public class User extends BaseModel{
    /** 用户微信id */
    protected String wxId;
    /** 用户微信名 */
    protected String wxName;
    /** 用户微信头像 */
    protected String wxAvatarUrl;
    /** 用户姓名 */
    protected String realName;
    /** 用户类别 */
    protected String userType;

    public String getWxId() {
        return wxId;
    }

    public User setWxId(String wxId) {
        this.wxId = wxId;
        return this;
    }

    public String getWxName() {
        return wxName;
    }

    public User setWxName(String wxName) {
        this.wxName = wxName;
        return this;
    }

    public String getWxAvatarUrl() {
        return wxAvatarUrl;
    }

    public void setWxAvatarUrl(String wxAvatarUrl) {
        this.wxAvatarUrl = wxAvatarUrl;
    }

    public String getRealName() {
        return realName;
    }

    public User setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public User setUserType(String userType) {
        this.userType = userType;
        return this;
    }

}
