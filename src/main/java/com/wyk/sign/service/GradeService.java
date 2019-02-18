/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.TbElective;
import com.wyk.sign.model.TbGrade;
import com.wyk.sign.model.TbStudent;

/**
 * @author wyk
 *
 */
public interface GradeService extends BaseService<TbGrade> {

    /**
     * 获取平时成绩（默认）
     * @param tbElective
     * @param tbStudent
     * @return
     */
    Integer getCourseScore(TbElective tbElective, TbStudent tbStudent);

}
