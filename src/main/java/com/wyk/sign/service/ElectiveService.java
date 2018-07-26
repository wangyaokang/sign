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
public interface ElectiveService extends BaseService<Elective> {


    /**
     * 通过adminId 或 classId 获取课程列表
     * @param param
     * @return
     */
    List<Course> getCourseList(Map<String, Object> param);

    /**
     * 通过courseId 或 adminId 获取班级列表
     * @param param
     * @return
     */
    List<Classes> getClassesList(Map<String, Object> param);

    /**
     * 通过courseId 或 classId 获取授课老师列表
     * @param param
     * @return
     */
    List<Administrator> getTeacherList(Map<String, Object> param);


    /**
     * 根据课程id、教师id、班级id删除授课信息
     * @param param
     * @return
     */
    int deleteElectiveByMap(Map<String, Object> param);
}
