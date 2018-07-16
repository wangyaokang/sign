package com.wyk.sign.model;

public class User extends BaseModel{

    /** 用户微信id */
    private String wxId;
    /** 用户微信名 */
    private String wxName;
    /** 用户微信头像 */
    private String wxPortraitUrl;
    /** 用户姓名 */
    private String realName;
    /** 用户类别 */
    private String userType;
    /** 对应的目标对象 */
    private Object target;

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

    public String getWxPortraitUrl() {
        return wxPortraitUrl;
    }

    public User setWxPortraitUrl(String wxPortraitUrl) {
        this.wxPortraitUrl = wxPortraitUrl;
        return this;
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

    public Object getTarget() {
        return target;
    }

    public User setTarget(Object target) {
        this.target = target;
        return this;
    }
}
