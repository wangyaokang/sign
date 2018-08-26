/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.Sign;

import java.util.List;

/**
 * @author wyk
 *
 */
public interface SignService extends BaseService<Sign> {

    /**
     * 根据infoId获取班级中全部学生的签到
     *
     * @param infoId
     * @return
     */
    List<Sign> querySignListOfClassByInfoId(String infoId);
}
