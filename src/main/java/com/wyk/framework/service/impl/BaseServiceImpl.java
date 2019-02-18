/**
 *
 */
package com.wyk.framework.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected BaseMapper<T> mapper;

    @Override
    public T get(Long id) {
        if (id == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        return mapper.get(map);
    }

    @Override
    public T get(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        return mapper.get(map);
    }

    @Override
    public List<T> query() {
        return mapper.query();
    }

    @Override
    public List<T> query(Paginator page) {
        if (null != page || page.getTotalPage() == 0) {
            return null;
        }

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
    }

    @Override
    public Integer insertBatch(List<T> list){
        if (list == null || list.size() == 0) {
            return null;
        }

        return mapper.insertBatch(list);
    }

    @Override
    public void update(T entity) {
        if (entity == null) {
            return;
        }

        mapper.update(entity);
    }

    @Override
    public Integer updateBatch(List<T> list){
        if (list == null || list.size() == 0) {
            return null;
        }

       return mapper.updateBatch(list);
    }

    @Override
    public void delete(T entity) {
        if (entity == null) {
            return;
        }

        mapper.delete(entity);
    }

}
