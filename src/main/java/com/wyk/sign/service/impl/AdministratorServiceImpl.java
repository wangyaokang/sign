/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.sign.model.TbAdmin;
import com.wyk.sign.service.AdministratorService;
import org.springframework.stereotype.Service;

import com.wyk.framework.service.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class AdministratorServiceImpl extends BaseServiceImpl<TbAdmin> implements AdministratorService {

    @Override
    public TbAdmin getUserByToken(String token) {
        Map<String, Object> param = new HashMap<>();
        param.put("wxId", token);
        TbAdmin user = get(param);
        return user;
    }

    @Override
    public boolean hasCacheAdministrator(String token) {
        String tokenKey = String.format("AdministratorServiceImpl_%s", token);
        return cacheMap.get(tokenKey) != null;
    }

    @Override
    public void flushCache() {
        cacheMap.removeAll();
    }

}
