/**
 * 
 */
package com.wyk.sign.service.impl;

import com.wyk.framework.service.impl.BaseServiceImpl;
import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.model.*;
import com.wyk.sign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyk
 *
 */
@Service
public class GradeServiceImpl extends BaseServiceImpl<Grade> implements GradeService {

    @Autowired
    SignInfoService signInfoService;

    @Autowired
    SignService signService;

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    TaskService taskService;

    @Override
    public double getCourseScore(boolean hasSign, boolean hasTask, Grade grade) {
        Elective elective = grade.getElective();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("classId", elective.getClasses().getId());
        param.put("courseId", elective.getCourse().getId());
        param.put("termStartDate", DateUtil.format(grade.getTermStartDate(), DateUtil.DATE_FORMAT));
        param.put("termStopDate", DateUtil.format(grade.getTermStopDate(), DateUtil.DATE_FORMAT));
        double signScore = 100, taskScore = 100;
        if(hasSign){
            // 查询出全部签到信息
            List<SignInfo> signInfoList = signInfoService.query(param);
            List<Sign> signList = signService.query(param);
            signScore = signInfoList.size() == 0 ? 100 : (signList.size() / signInfoList.size()) * 100;
        }

        if(hasTask){
            // 查询出全部的作业信息
            List<TaskInfo> taskInfoList = taskInfoService.query(param);
            List<Task> taskList = taskService.query(param);
            double total = 0;
            for(Task task : taskList){
                total += task.getScore();
            }
            taskScore = taskInfoList.size() == 0 ? 100 : (total / taskInfoList.size());
        }
        return signScore * 0.5 + taskScore * 0.5;
    }
}
