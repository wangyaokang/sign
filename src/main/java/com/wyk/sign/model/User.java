/**
 * 
 */
package com.wyk.sign.model;

import java.util.Date;

/**
 * @author Dareen-Leo
 *
 */
public class User extends BaseModel {

	private static final long serialVersionUID = 3474323268481100686L;
	
	/** 匿名用户 */
	public static final User Anonymous;

	/** 用户名 */
	private String username;
	
	/** 密码 */
	private String password;
	
	/** 手机号 */
	private String mobile;
	
	/** 昵称 */
	private String nickname;
	
	/** 性别 */
	private Integer sex;
	
	/** 注册时间 */
	private Date regTime;
	
	/**
	 * 匿名用户初始化
	 */
	static {
		Anonymous = new User();
		Anonymous.setId(0L);
		Anonymous.setUsername("anonymous");
		Anonymous.setNickname("匿名用户");
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the regTime
	 */
	public Date getRegTime() {
		return regTime;
	}

	/**
	 * @param regTime the regTime to set
	 */
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
}
