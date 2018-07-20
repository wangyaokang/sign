/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.sign.model.Classes;
import com.wyk.sign.model.User;
import com.wyk.framework.service.BaseService;

/**
 * @author wyk
 *
 */
public interface ClassesService extends BaseService<Classes> {

    /**
     * 匿名登录
     *
     * @return
     */
    User getAnonymous();

    /**
     * 根据token获取user对象
     * @param token
     * @return
     */
    User getUserByToken(String token);

    /**
     * 保存user
     * @param user
     */
    void saveUser(User user);

}
