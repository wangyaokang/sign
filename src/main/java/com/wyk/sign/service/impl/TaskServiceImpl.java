/**
 *
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Task;
import com.wyk.sign.persistence.TaskMapper;
import com.wyk.sign.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyk
 */
@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {

    @Autowired
    TaskMapper mapper;

    @Override
    public void deleteByInfoId(Integer infoId) {
        cacheMap.removeContainsKey(this.getClass().getSimpleName());
        mapper.deleteByInfoId(infoId);
    }
}
