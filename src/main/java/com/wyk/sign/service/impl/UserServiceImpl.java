/**
 * 
 */
package com.wyk.sign.service.impl;

import org.springframework.stereotype.Service;

import com.wyk.sign.model.User;
import com.wyk.sign.service.UserService;
import com.wyk.framework.service.impl.BaseServcieImpl;

/**
 * @author Dareen-Leo
 *
 */
@Service
public class UserServiceImpl extends BaseServcieImpl<User> implements UserService {

	/* (non-Javadoc)
	 * @see com.wyk.sign.service.UserService#getAnonymous()
	 */
	@Override
	public User getAnonymous() {
		return User.Anonymous;
	}

}
