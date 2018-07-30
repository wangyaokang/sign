/**
 *
 */
package com.wyk.framework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyk.framework.cache.impl.LocalCacheManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.wyk.framework.entity.AutoIdEntity;
import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.framework.page.PageBounds;
import com.wyk.framework.page.Paginator;
import com.wyk.framework.service.BaseService;

import javax.swing.*;

/**
 * Service的CRUD基类
 *
 * @author wyk
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    protected LocalCacheManagerImpl cacheMap;

    @Autowired
    protected BaseMapper<T> mapper;

    @Override
    public T get(Long id) {
        if (id == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);

        String tokenKey = this.getClass().getSimpleName() + id;
        if (null != cacheMap.get(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (T) cacheMap.get(tokenKey);
        }

        T obj = mapper.get(map);
        if (null != obj) {
            logger.info("设置缓存key:{} value:{}", tokenKey, obj);
            cacheMap.set(tokenKey, obj);
        }
        return obj;
    }

    @Override
    public T get(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        String tokenKey = this.getClass().getSimpleName() + map.get("wxId");
        if (null != cacheMap.get(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (T) cacheMap.get(tokenKey);
        }

        T obj = mapper.get(map);
        if (null != obj) {
            logger.info("设置缓存key:{} value:{}", tokenKey, obj);
            cacheMap.set(tokenKey, obj);
        }
        return obj;
    }

    @Override
    public List<T> query() {
        String tokenKey = this.getClass().getSimpleName() + "query";
        if (null != cacheMap.get(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (List<T>) cacheMap.get(tokenKey);
        }

        List<T> objList = mapper.query();
        if (null != objList && objList.size() != 0) {
            logger.info("设置缓存key:{} value:{}", tokenKey, objList);
            cacheMap.set(tokenKey, objList);
        }

        return objList;
    }

    @Override
    public List<T> query(Paginator page) {
        return mapper.query(PageBounds.wrap(page));
    }

    @Override
    public List<T> query(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return query();
        }
        return mapper.queryByMap(map);
    }

    @Override
    public List<T> query(Map<String, Object> map, Paginator page) {
        if (map == null || map.isEmpty()) {
            return query(page);
        }
        return mapper.queryByMap(map, PageBounds.wrap(page));
    }

    @Override
    public void insert(T entity) {
        if (entity == null) {
            return;
        }
        mapper.insert(entity);

        String tokenKey = this.getClass().getSimpleName() + "query";
        if(null != cacheMap.get(tokenKey)){
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }
    }

    @Override
    public void update(T entity) {
        if (entity == null) {
            return;
        }

        mapper.update(entity);

        String tokenKey = this.getClass().getSimpleName() + ((AutoIdEntity) entity).getId();
        if (null != cacheMap.get(tokenKey)) {
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }

        tokenKey = this.getClass().getSimpleName() + "query";
        if(null != cacheMap.get(tokenKey)){
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }
    }

    @Override
    public void save(T entity) {
        if (!(entity instanceof AutoIdEntity)) {
            return;
        }
        AutoIdEntity baseEntity = (AutoIdEntity) entity;
        if (baseEntity.getId() == null || baseEntity.getId() == 0) {
            insert(entity);
        } else {
            update(entity);
        }
    }

    @Override
    public void delete(T entity) {
        if (entity == null) {
            return;
        }

        mapper.delete(entity);
        String tokenKey = this.getClass().getSimpleName() + ((AutoIdEntity) entity).getId();
        if (null != cacheMap.get(tokenKey)) {
            logger.info("删除缓存key:{} ", tokenKey);
           cacheMap.delete(tokenKey);
        }

        tokenKey = this.getClass().getSimpleName() + "query";
        if(null != cacheMap.get(tokenKey)){
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }
    }

}
