/**
 * 
 */
package com.wyk.sign.util;

/**
 * 常量
 * 
 * @author wyk
 *
 */
public abstract class Constants {

	public abstract static class User{
		/** userType 为学生 */
		public static final Integer STU = 1;
		/** userType 为管理员（教师或辅导员） */
		public static final Integer ADMIN = 2;
	}

	public abstract static class Sign{
		/** 未签到 */
		public static final Integer NOT_SIGNED = 0;
		/** 迟到 */
		public static final Integer LATE = 1;
		/** 签到 */
		public static final Integer SIGNED = 2;
	}
}

	

