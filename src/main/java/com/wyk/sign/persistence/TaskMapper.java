/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.Task;
import org.springframework.stereotype.Repository;


/**
 * @author wyk
 *
 */
@Repository
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 删除作业信息下的所有作业
     * <p>传入参数</p>
     *
     * <pre>作业信息ID</pre>
     *
     * @param infoId
     * return
     */
    void deleteByInfoId(Integer infoId);
}
