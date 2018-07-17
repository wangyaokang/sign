/**
 * 
 */
package com.wyk.sign.persistence;

import org.springframework.stereotype.Repository;

import com.wyk.sign.model.User;
import com.wyk.framework.mybatis.BaseMapper;


/**
 * @author wyk
 *
 */
@Repository
public interface StudentMapper extends BaseMapper<User> {

}
