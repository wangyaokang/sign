/**
 * 
 */
package com.wyk.framework.service;

import java.util.List;
import java.util.Map;

import com.wyk.framework.page.Paginator;

/**
 * CURD 服务基类
 * 
 * @author wyk
 *
 */
public interface BaseService<T> {

	/**
	 * 获得实体
	 * 
	 * @param id
	 * @return
	 */
	T get(Long id);
	
	/**
	 * 获得实体
	 * 
	 * @param map
	 * @return
	 */
	T get(Map<String, Object> map);
	
	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<T> query();
	
	/**
	 * 查询全部（分页）
	 *
	 * @param page
	 * @return
	 */
	List<T> query(Paginator page);
	
	/**
	 * 按条件查询
	 *
	 * @param map
	 * @return
	 */
	List<T> query(Map<String, Object> map);
	
	/**
	 * 按条件查询（分页）
	 *
	 * @param map
	 * @param page
	 * @return
	 */
	List<T> query(Map<String, Object> map, Paginator page);
	
	/**
	 * 插入
	 *
	 * @param entity
	 */
	void insert(T entity);

	/**
	 * 批量插入
	 *
	 * @param entity
	 * @return
	 */
	Integer insertBatch(List<T> entity);
	
	/**
	 * 更新
	 *
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 批量更新
	 *
	 * @param entity
	 */
	Integer updateBatch(List<T> entity);
	
	/**
	 * 删除
	 *
	 * @param entity
	 */
	void delete(T entity);

}
