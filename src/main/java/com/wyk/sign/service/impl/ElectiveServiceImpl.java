/**
 *
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.TbAdmin;
import com.wyk.sign.model.TbClass;
import com.wyk.sign.model.TbCourse;
import com.wyk.sign.model.TbElective;
import com.wyk.sign.mapper.TbElectiveMapper;
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
public class ElectiveServiceImpl extends BaseServiceImpl<TbElective> implements ElectiveService {

    @Autowired
    TbElectiveMapper mapper;

    @Override
    public List<TbCourse> getCourseList(Map<String, Object> param) {
        List<TbCourse> tbCourseList = new ArrayList<>();
        List<TbElective> tbElectiveList = query(param);

        if(tbElectiveList.size() != 0){
            for(TbElective tbElective : tbElectiveList){
                TbCourse tbCourse = tbElective.getTbCourse();
                tbCourseList.add(tbCourse);
            }
        }

        return tbCourseList;
    }

    @Override
    public List<TbClass> getClassesList(Map<String, Object> param) {
        List<TbClass> TbClassList = new ArrayList<>();
        List<TbElective> tbElectiveList = query(param);

        if(tbElectiveList.size() != 0){
            for(TbElective tbElective : tbElectiveList){
                // 查询的信息不全，重新获取
                TbClass TbClass = tbElective.getTbClass();
                TbClassList.add(TbClass);
            }
        }

        return TbClassList;
    }

    @Override
    public List<TbAdmin> getTeacherList(Map<String, Object> param) {
        List<TbAdmin> teacherList = new ArrayList<>();
        List<TbElective> tbElectiveList = query(param);

        if(tbElectiveList.size() != 0){
            for(TbElective tbElective : tbElectiveList){
                TbAdmin teacher = tbElective.getAdmin();
                teacherList.add(teacher);
            }
        }

        return teacherList;
    }

}
