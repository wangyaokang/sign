/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.TbSignInfo;
import com.wyk.sign.mapper.TbSignInfoMapper;
import com.wyk.sign.service.TbSignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class TbTbSignInfoServiceImpl extends BaseServiceImpl<TbSignInfo> implements TbSignInfoService {

    @Autowired
    TbSignInfoMapper mapper;

    @Override
    public List<String> getSignDatesByCurMonth(Map<String, Object> map) {
        String tokenKey = String.format("SignInfoServiceImpl_query_map_%s", map.toString());
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

    @Override
    public void batchSave(List<TbSignInfo> tbSignInfoList) {
        mapper.batchSave(tbSignInfoList);
        String tokenKey = "TbTbSignInfoServiceImpl";
        cacheMap.removeContainsKey(tokenKey);
        logger.info("删除包含 {} 的所有缓存！", tokenKey);
    }
}
