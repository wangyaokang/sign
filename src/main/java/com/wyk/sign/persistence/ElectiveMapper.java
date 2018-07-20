/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.User;
import org.springframework.stereotype.Repository;


/**
 * @author wyk
 *
 */
@Repository
public interface AdministratorMapper extends BaseMapper<User> {

}
