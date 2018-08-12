/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.SignInfo;
import com.wyk.sign.persistence.SignInfoMapper;
import com.wyk.sign.service.SignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class SignInfoServiceImpl extends BaseServiceImpl<SignInfo> implements SignInfoService {

    @Autowired
    SignInfoMapper mapper;

    @Override
    public List<String> getSignDatesByCurMonth(Map<String, Object> map) {
        String tokenKey = String.format("%s_query_map_%s", this.getClass().getSimpleName(), map.keySet().toString());
        if (null != cacheMap.getContainsKeyOfValue(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (List<String>) cacheMap.getContainsKeyOfValue(tokenKey);
        }

        List<String> objList = mapper.getSignDatesByCurMonth(map);
        if(null != objList && objList.size() != 0){
            logger.info("设置缓存key:{} value:{}", tokenKey, objList);
            cacheMap.set(tokenKey, objList);
        }

        return objList;
    }
}
