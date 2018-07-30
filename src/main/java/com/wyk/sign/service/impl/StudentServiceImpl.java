/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Student;
import com.wyk.sign.model.User;
import com.wyk.sign.persistence.StudentMapper;
import com.wyk.sign.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getAnonymous() {
        return (Student) User.Anonymous;
    }

    @Override
    public Student getUserByToken(String token) {
        Map<String, Object> param = new HashMap<>();
        param.put("wxId", token);
        Student user = get(param);
        return user;
    }

    @Override
    public int batchUpdate(List<Student> studentList) {
        return studentMapper.batchUpdate(studentList);
    }

}
