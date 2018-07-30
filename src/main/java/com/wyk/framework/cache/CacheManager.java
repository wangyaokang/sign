/**
 * 
 */
package com.wyk.framework.cache;

/**
 * Cache管理方法
 * <p>
 * 在Spring的配置文件中需要维护 cacheManager 的bean， 如下： <br/>
 * &lt;bean id="cacheManager" class="com.wyk.framework.cache.impl.CacheManagerImpl"
 * /&gt;
 * </p>
 * 
 * @author wyk
 * 
 */
public interface CacheManager {

	/**
	 * 获取key对应的键值
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 设置对应的键值对
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	<T> void set(String key, T value);

	/**
	 * 替换key对应的键值
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	<T> void replace(String key, T value);

	/**
	 * 删除此条键值对
	 *
	 * @param key
	 */
	void delete(String key);

	/**
	 * 删除包含key的所有的键值对
	 *
	 * @param key
	 */
	void removeContainsKey(String key);

	/**
	 * 删除所有的键值对
	 */
	void removeAll();
}
