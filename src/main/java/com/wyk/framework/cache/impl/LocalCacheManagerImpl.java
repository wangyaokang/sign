/**
 * 
 */
package com.wyk.framework.cache.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.wyk.framework.cache.CacheManager;

/**
 * @author bocar
 *
 */
public class LocalCacheManagerImpl implements CacheManager {
	
	private static ConcurrentMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	/* (non-Javadoc)
	 * @see com.webxframework.core.cache.CacheManager#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return map.get(key);
	}

	/* (non-Javadoc)
	 * @see com.webxframework.core.cache.CacheManager#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> void set(String key, T value) {
		map.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.webxframework.core.cache.CacheManager#replace(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T> void replace(String key, T value) {
		map.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.webxframework.core.cache.CacheManager#delete(java.lang.String)
	 */
	@Override
	public void delete(String key) {
		map.remove(key);
	}

}
