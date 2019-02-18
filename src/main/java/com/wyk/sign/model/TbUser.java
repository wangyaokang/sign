package com.wyk.sign.model;

/**
 * 用户基类
 *
 * @author wyk
 */
public class TbUser extends BaseModel{

    private static final long serialVersionUID = -3387809950289571218L;

    /** 用户微信id */
    protected String wxId;

    /** 用户微信名 */
    protected String wxName;

    /** 用户微信头像 */
    protected String wxAvatarUrl;

    /** 用户姓名 */
    protected String realName;

    /**
     * 用户类别
     *
     * <pre> 1: 教师，2：辅导员，3：教务主任，4：学生</pre>
     *
     * */
    protected Integer userType;

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
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

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
