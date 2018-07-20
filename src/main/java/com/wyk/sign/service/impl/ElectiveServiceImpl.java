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
import com.wyk.sign.service.ClassesService;
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
        List<Elective> electiveList = mapper.queryByMap(param);

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
        return null;
    }

    @Override
    public List<Administrator> getTeacherList(Map<String, Object> param) {
        return null;
    }
}
