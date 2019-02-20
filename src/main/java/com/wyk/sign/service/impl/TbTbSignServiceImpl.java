/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.TbSign;
import com.wyk.sign.mapper.TbSignMapper;
import com.wyk.sign.service.TbSignService;
import org.apache.commons.lang.StringUtils;
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
public class TbTbSignServiceImpl extends BaseServiceImpl<TbSign> implements TbSignService {

    @Autowired
    TbSignMapper mapper;

    @Override
    public List<TbSign> querySignListOfClassByInfoId(String infoId) {
        if(StringUtils.isEmpty(infoId)){
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", infoId);
        return mapper.querySignListOfClassByInfoId(param);
    }
}
