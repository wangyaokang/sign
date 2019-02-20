/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.TbSign;

import java.util.List;

/**
 * @author wyk
 *
 */
public interface TbSignService extends BaseService<TbSign> {

    /**
     * 根据infoId获取班级中全部学生的签到
     *
     * @param infoId
     * @return
     */
    List<TbSign> querySignListOfClassByInfoId(String infoId);
}
