/**
 * 
 */
package com.wyk.sign.model;

import java.util.Date;

import com.wyk.framework.entity.AutoIdEntity;

/**
 * 业务基础实体类
 * 
 * @author Dareen-Leo
 *
 */
@SuppressWarnings("serial")
public abstract class BaseModel extends AutoIdEntity {

	public static final int INIT = 0;
	
	/**
	 * 状态，具体实体类自定义
	 */
	protected Integer status = INIT;
	
	/** 新增时间 */
	protected Date createTime;
	
	/** 最后修改时间 */
	protected Date modifyTime;

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
