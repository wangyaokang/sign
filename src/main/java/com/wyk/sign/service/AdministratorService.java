/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.TbAdmin;

/**
 * @author wyk
 *
 */
public interface AdministratorService extends BaseService<TbAdmin> {


    /**
     * 根据token获取admin对象
     * @param token
     * @return
     */
    TbAdmin getUserByToken(String token);

    /**
     * 判断缓存中是否存在Administrator对象
     *
     * @param token
     * @return
     */
    boolean hasCacheAdministrator(String token);

    /**
     * 刷新缓存
     */
    void flushCache();
}
