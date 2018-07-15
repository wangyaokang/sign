/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.sign.model.User;
import com.wyk.framework.service.BaseService;

/**
 * @author Dareen-Leo
 *
 */
public interface UserService extends BaseService<User> {

	public User getAnonymous();
	
}
