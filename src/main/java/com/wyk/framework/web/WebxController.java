/**
 * 
 */
package com.wyk.framework.web;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author wyk
 *
 */
public interface WebxController {

	ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * JSON返回参数
	 * <ul>
	 * <li>0: 表示正常返回
	 * <li>非0: 表示出错信息
	 * </ul>
	 */
	String RETURN_CODE = "returnCode";
	
	/** JSON返回信息 */
	String MSG = "msg";
	
	/** JSON数据的Key - data */
	String DATA = "data";
	
	/** JSON数据的Key - list */
	String LIST = "list";
	
	/** JSON数据的Key - page */
	String PAGE = "page";
	
	/** JSON数据的Key - rows */
	String ROWS = "rows";
	
	/** JSON数据的Key - total */
	String TOTAL = "total";
	
	/** 正确返回 */
	String SUCCESS = "200";
	
	/** 错误返回: 没有指定数据 */
	String ERROR_NO_RECORD = "400";
	
	/** 未知错误 */
	String ERROR_UNKNOWN = "500";
}
