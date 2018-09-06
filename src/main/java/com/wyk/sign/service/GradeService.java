/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.Grade;

/**
 * @author wyk
 *
 */
public interface GradeService extends BaseService<Grade> {


    /**
     * 获取平时成绩
     * @param hasTask
     * @param hasSign
     * @param grade
     * @return
     */
    double getCourseScore(boolean hasTask, boolean hasSign, Grade grade);
}
