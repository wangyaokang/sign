/**
 * 
 */
package com.wyk.sign.persistence;

import org.springframework.stereotype.Repository;

import com.wyk.sign.model.UserToken;
import com.wyk.framework.mybatis.BaseMapper;

/**
 * @author Dareen-Leo
 *
 */
@Repository
public interface UserTokenMapper extends BaseMapper<UserToken> {

}
