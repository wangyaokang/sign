package com.wyk.sign.model;

import java.util.List;

public class Classes extends BaseModel{
    /** 班级名称 */
    private String classname;
    /** 班级创建者 */
    private String createUsername;
    /** 班级成员 */
    private List<User> userList;

    public String getClassname() {
        return classname;
    }

    public Classes setClassname(String classname) {
        this.classname = classname;
        return this;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public Classes setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
        return this;
    }

    public List<User> getUserList() {
        return userList;
    }

    public Classes setUserList(List<User> userList) {
        this.userList = userList;
        return this;
    }
}
