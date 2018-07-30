/**
 * 
 */
package com.wyk.framework.service;

import java.util.List;
import java.util.Map;

import com.wyk.framework.page.Paginator;

/**
 * Single Entity<T> CRUD Service
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
	 * 插叙
	 * 
	 * @return
	 */
	List<T> query();
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	List<T> query(Paginator page);
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	List<T> query(Map<String, Object> map);
	
	/**
	 * 
	 * @param map
	 * @param page
	 * @return
	 */
	List<T> query(Map<String, Object> map, Paginator page);
	
	/**
	 * 
	 * @param entity
	 */
	void insert(T entity);
	
	/**
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 *
	 * @param entity
	 */
	void updateBatch(List<T> entity);
	
	/**
	 * Only For AutoIdEntity
	 * 
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 
	 * @param entity
	 */
	void delete(T entity);

	/**
	 *
	 * @param map
	 */
	void deleteByMap(Map<String, Object> map);
}
