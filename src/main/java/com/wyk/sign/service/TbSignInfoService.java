/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.TbSignInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
public interface TbSignInfoService extends BaseService<TbSignInfo> {

    /**
     * 获取当前月份的签到日期
     *
     * @param map {curMonth : yyyy-MM}
     * @return
     */
    List<String> getSignDatesByCurMonth(Map<String, Object> map);

    /**
     * 批量插入
     * @param tbSignInfoList
     */
    void batchSave(List<TbSignInfo> tbSignInfoList);
}
