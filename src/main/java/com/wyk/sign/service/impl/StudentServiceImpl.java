/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.sign.model.Administrator;
import com.wyk.sign.persistence.AdministratorMapper;
import com.wyk.sign.persistence.StudentMapper;
import com.wyk.sign.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyk.sign.model.User;
import com.wyk.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class AdministratorServiceImpl extends BaseServiceImpl<Administrator> implements AdministratorService {

    @Autowired
    private AdministratorMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public User getAnonymous() {
        return User.Anonymous;
    }

    @Override
    public Administrator getUserByToken(String token) {
        Map<String, Object> param = new HashMap<>();
        param.put("wxId", token);
        Administrator user = adminMapper.get(param);
        return user;
    }

}
