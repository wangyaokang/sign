/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
public interface TbElectiveService extends BaseService<TbElective> {


    /**
     * 通过adminId 或 classId 获取课程列表
     * @param param
     * @return
     */
    List<TbCourse> getCourseList(Map<String, Object> param);

    /**
     * 通过courseId 或 adminId 获取班级列表
     * @param param
     * @return
     */
    List<TbClass> getClassesList(Map<String, Object> param);

    /**
     * 通过courseId 或 classId 获取授课老师列表
     * @param param
     * @return
     */
    List<TbAdmin> getTeacherList(Map<String, Object> param);
}
