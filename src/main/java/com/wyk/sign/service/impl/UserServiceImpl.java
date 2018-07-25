/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.Student;
import com.wyk.sign.persistence.AdministratorMapper;
import com.wyk.sign.persistence.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyk.sign.model.User;
import com.wyk.sign.service.UserService;
import com.wyk.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private AdministratorMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public User getAnonymous() {
        return User.Anonymous;
    }

    @Override
    public User getUserByToken(String token) {
        Map<String, Object> param = new HashMap<>();
        param.put("wxId", token);
        User user = adminMapper.get(param);
        if(null == user){
            user = studentMapper.get(param);
        }
        return user;
    }

    @Override
    public void saveUser(User user){
        Integer userType = user.getUserType();
        if(userType == 1){
            studentMapper.insert((Student) user);
            logger.info("学生【{}】保存学生成功！", user.getRealName());
        }else if(userType == 2){
            adminMapper.insert((Administrator) user);
            logger.info("管理者【{}】保存成功！", user.getRealName());
        }
    }

    public static void main(String[] args) {
        System.out.println("NIJJJ");
    }

}
