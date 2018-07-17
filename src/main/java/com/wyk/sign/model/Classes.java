package com.wyk.sign.model;

import java.util.List;

/**
 *
 * 班级
 *
 */
public class Classes extends BaseModel{
    /** 班级名称 */
    private String classname;
    /** 班级创建者 */
    private String creator;
    /** 班级成员 */
    private List<User> userList;

    public String getClassname() {
        return classname;
    }

    public Classes setClassname(String classname) {
        this.classname = classname;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<User> getUserList() {
        return userList;
    }

    public Classes setUserList(List<User> userList) {
        this.userList = userList;
        return this;
    }
}
