/**
 * 
 */
package com.wyk.sign.util;

import java.util.Date;

import com.wyk.sign.model.User;

/**
 * 用于Mock的工具类
 * 
 * @author Dareen-Leo
 *
 */
public class MockHelper {

	/**
	 * 获得用户信息
	 * 
	 * @return
	 */
	public static User getUser() {
		User user = new User();
		user.setUsername("用户名");
		user.setMobile("18901780827");
		user.setNickname("昵称");
		user.setSex(1);
		user.setRegTime(new Date());
		return user;
	}
}
