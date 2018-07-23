/**
 * 
 */
package com.wyk.framework.mybatis;

import java.util.List;
import java.util.Map;

import com.wyk.framework.page.PageBounds;

/**
 * Mapper 基类
 * 
 * @author wyk
 *
 */
public interface BaseMapper<T> {

	/**
	 * 获得实体
	 * 
	 * @param map
	 * @return
	 */
	T get(Map<String, Object> map);
	
	/**
	 * 
	 * @return
	 */
	List<T> query();
	
	/**
	 * 
	 * @param pageBounds
	 * @return
	 */
	List<T> query(PageBounds pageBounds);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @param pageBounds
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> map, PageBounds pageBounds);
	
	/**
	 * 新增实体
	 * 
	 * @param entity
	 */
	void insert(T entity);
	
	/**
	 * 修改实体
	 * 
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(T entity);
}
