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
		/** userType 为教师 */
		public static final Integer TEACHER = 1;
		/** userType 为教师 */
		public static final Integer COUNSELLOR = 2;
		/** userType 为学生 */
		public static final Integer STUDENT = 3;
	}

	public abstract static class Sign{
		/** 未签到 */
		public static final Integer NOT_SIGNED = 0;
		/** 迟到 */
		public static final Integer LATE = 1;
		/** 签到 */
		public static final Integer SIGNED = 2;
		/** 申请补签 */
		public static final Integer SUPPLE_SIGN = 3;
	}
}



