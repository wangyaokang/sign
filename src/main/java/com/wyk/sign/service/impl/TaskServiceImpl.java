/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.sign.model.Sign;
import com.wyk.sign.model.Task;
import com.wyk.sign.persistence.SignMapper;
import com.wyk.sign.service.SignService;
import com.wyk.sign.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyk
 *
 */
@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {

}
