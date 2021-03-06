package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;

/**
 * 课程
 *
 * @author wyk
 */
public class TbCourse extends AutoIdEntity {

    private static final long serialVersionUID = -7522836192412894623L;

    /** 课程名 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
