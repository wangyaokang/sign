/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.Sign;
import org.springframework.stereotype.Repository;


/**
 * @author wyk
 *
 */
@Repository
public interface SignMapper extends BaseMapper<Sign> {

    /**
     * 删除签到信息下的所有签到
     * <p>传入参数</p>
     *
     * <pre>签到信息ID</pre>
     *
     * @param infoId
     * return
     */
    void deleteByInfoId(Integer infoId);
}
