/**
 * 
 */
package com.wyk.sign.web.api.param;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.wyk.sign.model.TbUser;
import com.wyk.framework.utils.DateUtil;

/**
 * 输入参数封装类
 *
 * @author wyk
 *
 */
public class Input implements Serializable {

	private static final long serialVersionUID = -10090147927893672L;

	/** 微信openid */
	private String openid;

	/** 执行的方法 */
	private String method;
	
	/** 业务参数 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/** 当前用户 */
	private TbUser currentTbUser;

	/**
	 * 
	 * @return
	 */
	public boolean isParamsEmpty() {
		return (params == null || params.isEmpty());
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return Integer.valueOf(params.get(key).toString());
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return Long.valueOf(params.get(key).toString());
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Float getFloat(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return Float.valueOf(params.get(key).toString());
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Double getDouble(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return Double.valueOf(params.get(key).toString());
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return params.get(key).toString();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Date getDate(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		String strDate = params.get(key).toString();
		Date date = DateUtil.parse(strDate, "yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			date = DateUtil.parse(strDate, "yyyy-MM-dd HH:mm");
		}
		if (date == null) {
			date = DateUtil.parse(strDate, "yyyy-MM-dd HH");
		}
		if (date == null) {
			date = DateUtil.parse(strDate, "yyyy-MM-dd");
		}
		return date;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Date getDate(String key, String format) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(params.get(key).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key) {
		if (params == null || params.isEmpty() || StringUtils.isEmpty(key) || params.get(key) == null) {
			return null;
		}
		return new BigDecimal(params.get(key).toString());
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(Class<T> clazz) {
		if (params == null || params.isEmpty()) {
			return null;
		}
		
		T bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, params);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the currentTbUser
	 */
	public TbUser getCurrentTbUser() {
		return currentTbUser;
	}

	/**
	 * @param currentTbUser the currentTbUser to set
	 */
	public void setCurrentTbUser(TbUser currentTbUser) {
		this.currentTbUser = currentTbUser;
	}
	
}
