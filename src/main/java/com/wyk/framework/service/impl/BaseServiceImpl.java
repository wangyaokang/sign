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

        String tokenKey = String.format("%s_%s", this.getClass().getSimpleName(), id);
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

        String tokenKey = String.format("%s_%s", this.getClass().getSimpleName(), map.get("wxId"));
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
        String tokenKey = String.format("%s_query", this.getClass().getSimpleName());
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
        if (null != page || page.getTotalPage() == 0) {
            return null;
        }

        String tokenKey = String.format("%s_query_pageNumber%s_pageSize%s", this.getClass().getSimpleName(), page.getPageNumber(), page.getPageSize());
        if (null != cacheMap.get(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (List<T>) cacheMap.get(tokenKey);
        }

        List<T> objList = mapper.query(PageBounds.wrap(page));
        if(null != objList && objList.size() != 0){
            logger.info("设置缓存key:{} value:{}", tokenKey, objList);
            cacheMap.set(tokenKey, objList);
        }

        return objList;
    }

    @Override
    public List<T> query(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return query();
        }

        String tokenKey = String.format("%s_query_map_%s", this.getClass().getSimpleName(), map.toString());
        if (null != cacheMap.getContainsKeyOfValue(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (List<T>) cacheMap.getContainsKeyOfValue(tokenKey);
        }

        List<T> objList = mapper.queryByMap(map);
        if(null != objList && objList.size() != 0){
            logger.info("设置缓存key:{} value:{}", tokenKey, objList);
            cacheMap.set(tokenKey, objList);
        }

        return mapper.queryByMap(map);
    }

    @Override
    public List<T> query(Map<String, Object> map, Paginator page) {
        if (map == null || map.isEmpty()) {
            return query(page);
        }

        String tokenKey = String.format("%s_query_map_%s_pageNumber%s_pageSize%s", this.getClass().getSimpleName(), map.toString(), page.getPageNumber(), page.getPageSize());
        if (null != cacheMap.getContainsKeyOfValue(tokenKey)) {
            logger.info("获取缓存key:{} ", tokenKey);
            return (List<T>) cacheMap.getContainsKeyOfValue(tokenKey);
        }

        List<T> objList = mapper.queryByMap(map);
        if(null != objList && objList.size() != 0){
            logger.info("设置缓存key:{} value:{}", tokenKey, objList);
            cacheMap.set(tokenKey, objList);
        }

        return mapper.queryByMap(map, PageBounds.wrap(page));
    }

    @Override
    public void insert(T entity) {
        if (entity == null) {
            return;
        }
        mapper.insert(entity);

        String tokenKey = String.format("%s_query", this.getClass().getSimpleName());
        logger.info("删除缓存key:{} ", tokenKey);
        cacheMap.removeContainsKey(tokenKey);
    }

    @Override
    public void update(T entity) {
        if (entity == null) {
            return;
        }

        mapper.update(entity);

        String tokenKey = String.format("%s_%s", this.getClass().getSimpleName(), ((AutoIdEntity) entity).getId());
        if (null != cacheMap.get(tokenKey)) {
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }

        tokenKey = String.format("%s_query", this.getClass().getSimpleName());
        logger.info("删除包含缓存key:{}的所有value！ ", tokenKey);
        cacheMap.removeContainsKey(tokenKey);
    }

    @Override
    public void updateBatch(List<T> list){
        if (list == null || list.size() == 0) {
            return;
        }

        mapper.updateBatch(list);
        String tokenKey = this.getClass().getSimpleName();
        logger.info("删除包含缓存key:{}的所有value！ ", tokenKey);
        cacheMap.removeContainsKey(tokenKey);
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
        String tokenKey = String.format("%s_%s", this.getClass().getSimpleName(), ((AutoIdEntity) entity).getId());
        if (null != cacheMap.get(tokenKey)) {
            logger.info("删除缓存key:{} ", tokenKey);
            cacheMap.delete(tokenKey);
        }

        tokenKey = String.format("%s_query", this.getClass().getSimpleName());
        logger.info("删除包含缓存key:{}的所有value！ ", tokenKey);
        cacheMap.removeContainsKey(tokenKey);
    }

    @Override
    public void deleteByMap(Map<String, Object> map){
        if (map == null || map.isEmpty()) {
            return ;
        }

        mapper.deleteByMap(map);
        String tokenKey = this.getClass().getSimpleName();
        logger.info("删除包含缓存key:{}的所有value！ ", tokenKey);
        cacheMap.removeContainsKey(tokenKey);
    }

}
