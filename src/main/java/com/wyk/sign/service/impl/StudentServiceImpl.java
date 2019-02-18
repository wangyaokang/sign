/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.TbStudent;
import com.wyk.sign.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class StudentServiceImpl extends BaseServiceImpl<TbStudent> implements StudentService {


    @Override
    public TbStudent getUserByToken(String token) {
        Map<String, Object> param = new HashMap<>();
        param.put("wxId", token);
        TbStudent user = get(param);
        return user;
    }

    @Override
    public boolean hasCacheStudent(String token) {
        String tokenKey = String.format("StudentServiceImpl_%s", token);
        return cacheMap.get(tokenKey) != null;
    }

}
