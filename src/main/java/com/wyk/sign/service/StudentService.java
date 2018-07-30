/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.sign.model.Student;
import com.wyk.framework.service.BaseService;

import java.util.List;

/**
 * @author wyk
 *
 */
public interface StudentService extends BaseService<Student> {

    /**
     * 匿名登录
     *
     * @return
     */
    Student getAnonymous();

    /**
     * 根据token获取user对象
     * @param token
     * @return
     */
    Student getUserByToken(String token);

}
