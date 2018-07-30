/**
 *
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Classes;
import com.wyk.sign.model.Course;
import com.wyk.sign.model.Elective;
import com.wyk.sign.persistence.ElectiveMapper;
import com.wyk.sign.service.ElectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wyk
 */
@Service
public class ElectiveServiceImpl extends BaseServiceImpl<Elective> implements ElectiveService {

    @Autowired
    ElectiveMapper mapper;

    @Override
    public List<Course> getCourseList(Map<String, Object> param) {
        List<Course> courseList = new ArrayList<>();
        List<Elective> electiveList = query(param);

        if(electiveList.size() != 0){
            for(Elective elective : electiveList){
                Course course = elective.getCourse();
                courseList.add(course);
            }
        }

        return courseList;
    }

    @Override
    public List<Classes> getClassesList(Map<String, Object> param) {
        List<Classes> classesList = new ArrayList<>();
        List<Elective> electiveList = query(param);

        if(electiveList.size() != 0){
            for(Elective elective : electiveList){
                Classes classes = elective.getClasses();
                classesList.add(classes);
            }
        }

        return classesList;
    }

    @Override
    public List<Administrator> getTeacherList(Map<String, Object> param) {
        List<Administrator> teacherList = new ArrayList<>();
        List<Elective> electiveList = query(param);

        if(electiveList.size() != 0){
            for(Elective elective : electiveList){
                Administrator teacher = elective.getAdmin();
                teacherList.add(teacher);
            }
        }

        return teacherList;
    }

    @Override
    public int deleteElectiveByMap(Map<String, Object> param) {
        return mapper.deleteElectiveByMap(param);
    }
}
