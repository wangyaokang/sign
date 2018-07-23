/**
 * 
 */
package com.wyk.sign.service;

import com.wyk.framework.service.BaseService;
import com.wyk.sign.model.Task;

/**
 * @author wyk
 *
 */
public interface TaskService extends BaseService<Task> {

    /**
     * 删除作业信息下的所有作业
     * <p>传入参数</p>
     *
     * <pre>作业信息ID</pre>
     */
    void deleteByInfoId(Integer infoId);
}
