/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.Sign;

import java.util.Map;

/**
 * @author wyk
 *
 */
public interface SignService extends BaseService<Sign> {

    /**
     * 删除签到信息下的所有签到
     * <p>传入参数</p>
     *
     * <pre>签到信息ID</pre>
     */
    void deleteByInfoId(Integer infoId);
}
