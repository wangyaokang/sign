/**
 * 
 */
package com.wyk.sign.model;

import java.util.Date;

import com.wyk.framework.entity.AutoIdEntity;

/**
 * @author wyk
 *
 */
public class UserToken extends AutoIdEntity {

	private static final long serialVersionUID = -5206896393485548016L;

	/** 用户 */
	private User user;
	
	/** TOKEN String */
	private String token;
	
	/** 最后记录时间 */
	private Date lastTime;

	/**
	 * 
	 * @return
	 */
	public Long getUserId() {
		if (user == null) {
			return null;
		}
		return user.getId();
	}
	
	/**
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		if (userId == null) {
			return;
		}
		if (user == null) {
			user = new User();
		}
		user.setId(userId);
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}
