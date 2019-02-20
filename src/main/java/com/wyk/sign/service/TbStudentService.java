/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.sign.model.TbStudent;
import com.wyk.framework.service.BaseService;

/**
 * @author wyk
 *
 */
public interface TbStudentService extends BaseService<TbStudent> {

    /**
     * 根据token获取user对象
     * @param token
     * @return
     */
    TbStudent getUserByToken(String token);

    /**
     * 判断缓存中是否存在Student对象
     *
     * @param token
     * @return
     */
    boolean hasCacheStudent(String token);

}
