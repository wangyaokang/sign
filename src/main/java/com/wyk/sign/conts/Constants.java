/**
 * 
 */
package com.wyk.sign.conts;

/**
 * 业务常量
 * 
 * @author wyk
 *
 */
public abstract class Constants {
	/** 成功 */
	public static final String SUCCESS_CODE = "200";
	public static final String SUCCESS_MSG = "success";

	/** 失败 */
	public static final String FAIL_CODE = "400";
	public static final String FAIL_MSG = "fail";

	/** 错误*/
	public static final String ERROR_CODE = "500";
	public static final String ERROR_MSG = "error";

	public abstract static class Input{
		/** openid */
		public static final String OPENID = "openId";
	}

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
		public static final Integer NOT_SIGNED = 1;
		/** 签到 */
		public static final Integer SIGNED = 2;
		/** 迟到 */
		public static final Integer LATE = 3;
		/** 申请补签 */
		public static final Integer SUPPLE_SIGN = 4;
	}

	public abstract static class Task{
		/** 作业上交状态 - 未交 */
		public static final Integer UPLOAD_STATUS_WJ = 0;
		/** 作业上交状态 - 已交 */
		public static final Integer UPLOAD_STATUS_YJ = 1;
		/** 默认是60分 */
		public static final Integer DEFAULT_SCORE = 60;
		/** 未交是0分 */
		public static final Integer DEFAULT_MIN_SCORE = 0;
	}

}



