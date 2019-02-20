/**
 * 
 */
package com.wyk.sign.web.api.param;
import java.io.Serializable;

/**
 * 输出参数封装类
 *
 * @author wyk
 *
 */
public class Output<T> implements Serializable {

	private static final long serialVersionUID = -8271862834018775105L;

	/**
     * 操作状态代码（如：成功“200”等等）
     */
	private String status;
	
	 /**
     * 状态消息数据（如：“登录成功”）
     */
    private String msg;

    /** 返回数据 */
    private T data;

	public Output() {}

	public Output(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Output(String status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public static Output getSuccess(String status, String msg) {
		return new Output(status, msg);
	}

	public static <T> Output getSuccess(String status, String msg, T data){
		return new Output(status, msg, data);
	}

	public static Output getFail(String status, String msg) {
		return new Output(status, msg);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
}
