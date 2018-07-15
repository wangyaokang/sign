/**
 * 
 */
package com.wyk.sign.persistence;

import org.springframework.stereotype.Repository;

import com.wyk.sign.model.User;
import com.wyk.framework.mybatis.BaseMapper;


/**
 * @author Dareen-Leo
 *
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
