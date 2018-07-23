package com.wyk.sign.annotation;

/**
 * 数据检测
 *
 * <pre>
 *     用户类型：TYPE,
 *     学生：STU,
 *     管理者：ADMIN
 * </pre>
 *
 * @author wyk
 */
public enum Item {

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
