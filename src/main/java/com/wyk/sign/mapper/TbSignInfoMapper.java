/**
 * 
 */
package com.wyk.sign.mapper;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.TbSignInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface TbSignInfoMapper extends BaseMapper<TbSignInfo> {

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