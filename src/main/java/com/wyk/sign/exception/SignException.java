/**
 * 
 */
package com.wyk.sign.exception;

import static com.wyk.framework.web.WebxController.*;

/**
 * 异常类
 *
 * @author wyk
 *
 */
public class SignException extends RuntimeException {

	private static final long serialVersionUID = 8592777324864320498L;
	
	protected String status = ERROR_UNKNOWN;

	/**
	 * 
	 */
	public SignException() {
		super();
	}

	/**
	 * @param status
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SignException(String status, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.status = status;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param status
	 * @param message
	 * @param cause
	 */
	public SignException(String status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public SignException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param status
	 * @param message
	 */
	public SignException(String status, String message) {
		super(message);
		this.status = status;
	}
	
	/**
	 * @param message
	 */
	public SignException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SignException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
