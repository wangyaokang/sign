/**
 * 
 */
package com.wyk.framework.cache.impl;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.wyk.framework.cache.CacheManager;
import java.util.Map;

/**
 * @author wyk
 *
 */
public class LocalCacheManagerImpl implements CacheManager {
	
	private static ConcurrentMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	@Override
	public Object get(String key) {
		return map.get(key);
	}

	@Override
	public <T> void set(String key, T value) {
		map.put(key, value);
	}

	@Override
	public <T> void replace(String key, T value) {
		map.put(key, value);
	}

	@Override
	public void delete(String key) {
		map.remove(key);
	}

	@Override
	public void removeAll() {
		map.clear();
	}

	public boolean isContainsKey(String key){
		for(Object mapKey : map.keySet()){
			if(key.contains(mapKey.toString()) && mapKey.toString().contains(key)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void removeContainsKey(String key) {
		Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<Object, Object> item = iterator.next();
			String mapKey = item.getKey().toString();
			if(mapKey.contains(key)){
				iterator.remove();
			}
		}
	}

	public Object getContainsKeyOfValue(String key){
		for(Object mapKey : map.keySet()){
			if(key.contains(mapKey.toString()) && mapKey.toString().contains(key)){
				return map.get(mapKey);
			}
		}
		return null;
	}

}
