/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Sign;
import com.wyk.sign.persistence.SignMapper;
import com.wyk.sign.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyk
 *
 */
@Service
public class SignServiceImpl extends BaseServiceImpl<Sign> implements SignService {

    @Autowired
    SignMapper mapper;

    @Override
    public void deleteByInfoId(Integer infoId) {
        mapper.deleteByInfoId(infoId);
    }
}
