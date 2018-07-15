/**
 * 
 */
package com.wyk.sign.web.api.param;

import static com.wyk.framework.web.WebxController.*;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Dareen-Leo
 *
 */
public class Output implements Serializable {

	private static final long serialVersionUID = -8271862834018775105L;

	/**
     * 操作状态代码（如：成功“200”等等）
     */
	private String status = SUCCESS;
	
	 /**
     * 状态消息数据（如：“登录成功”）
     */
    private String msg;
    
    private Object data = new HashMap<String, Object>();

	/**
	 * 
	 */
	public Output() {}
	
	/**
	 * @param status
	 * @param msg
	 * @param data
	 */
	public Output(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	
	/**
	 * @param status
	 * @param msg
	 * @param data
	 */
	public Output(String status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
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

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
    
}
