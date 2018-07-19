package com.wyk.sign.annotation;

/**
 * 数据检测
 */
public enum CheckItem {

    /**
     * 用户类型
     *
     * <pre>userType 是否为空</pre>
     * */
    TYPE,

    /**
     * 学生
     *
     * <pre>realName姓名、class班级、sno学号是否为空</pre>
     */
    STU,

    /**
     * 管理者
     *
     * <pre>realName姓名、class是否为空</pre>
     */
    ADMIN
}
