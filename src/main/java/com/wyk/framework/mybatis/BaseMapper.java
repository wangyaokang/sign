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
	 * 查询全部
	 *
	 * @return
	 */
	List<T> query();

	/**
	 * 查询全部（分页）
	 *
	 * @param pageBounds
	 * @return
	 */
	List<T> query(PageBounds pageBounds);

	/**
	 * 按条件查询
	 *
	 * @param map
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> map);

	/**
	 * 按条件查询（分页）
	 *
	 * @param map
	 * @param pageBounds
	 * @return
	 */
	List<T> queryByMap(Map<String, Object> map, PageBounds pageBounds);

	/**
	 * 插入
	 *
	 * @param entity
	 */
	void insert(T entity);

	/**
	 * 批量插入
	 *
	 * @param list
	 * @return
	 */
	int insertBatch(List<T> list);

	/**
	 * 更新
	 *
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 批量更新
	 *
	 * @param list
	 */
	int updateBatch(List<T> list);

	/**
	 * 删除
	 *
	 * @param entity
	 */
	void delete(T entity);

}
