/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.SignInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface SignInfoMapper extends BaseMapper<SignInfo> {

    /**
     * 获取当前月份的签到日期
     *
     * @param map {curMonth : yyyy-MM}
     * @return
     */
    List<String> getSignDatesByCurMonth(Map<String, Object> map);
}