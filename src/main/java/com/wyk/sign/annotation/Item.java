package com.wyk.sign.annotation;

/**
 * 检查项目
 *
 */
public enum Item {

    /**
     * 用户类别
     *
     * <pre>userType 是否为空</pre>
     */
    TYPE,

    /**
     * 学生
     *
     * <pre>userType 是否为 1，sno 学号, classId 是否为空</pre>
     *
     */
    STU,

    /**
     * 管理人
     *
     * <pre>userType 是否为 2</pre>
     *
     */
    ADMIN
}
