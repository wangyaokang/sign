package com.wyk.sign.model;

import com.wyk.framework.entity.AutoIdEntity;

/**
 * 组别
 *
 * <pre>XX级XX专业XX组</pre>
 *
 * @author wyk
 */
public class TbGroup extends BaseModel {

    /** 组别名称 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
