/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.Administrator;
import com.wyk.sign.model.User;

/**
 * @author wyk
 *
 */
public interface AdministratorService extends BaseService<Administrator> {

    /**
     * 匿名登录
     *
     * @return
     */
    User getAnonymous();

    /**
     * 根据token获取admin对象
     * @param token
     * @return
     */
    Administrator getUserByToken(String token);

    /**
     * 判断缓存中是否存在Administrator对象
     *
     * @param token
     * @return
     */
    boolean hasCacheAdministrator(String token);
}
