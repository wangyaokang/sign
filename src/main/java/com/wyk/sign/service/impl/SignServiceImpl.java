/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Sign;
import com.wyk.sign.persistence.SignMapper;
import com.wyk.sign.service.SignService;
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
public class SignServiceImpl extends BaseServiceImpl<Sign> implements SignService {

    @Autowired
    SignMapper mapper;

    @Override
    public List<Sign> querySignListOfClassByInfoId(String infoId) {
        if(StringUtils.isEmpty(infoId)){
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", infoId);
        return mapper.querySignListOfClassByInfoId(param);
    }
}
