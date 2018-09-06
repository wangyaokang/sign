/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.Message;
import org.springframework.stereotype.Repository;


/**
 * @author wyk
 *
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

}
