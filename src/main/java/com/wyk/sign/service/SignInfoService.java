/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.SignInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
public interface SignInfoService extends BaseService<SignInfo> {

    /**
     * 获取当前月份的签到日期
     *
     * @param map {curMonth : yyyy-MM}
     * @return
     */
    List<String> getSignDatesByCurMonth(Map<String, Object> map);
}
