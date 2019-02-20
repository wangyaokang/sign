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
 */
@Service
public class TbTbGradeServiceImpl extends BaseServiceImpl<TbGrade> implements TbGradeService {

    @Autowired
    TbSignInfoService tbSignInfoService;

    @Autowired
    TbSignService tbSignService;

    @Autowired
    TbTaskInfoService tbTaskInfoService;

    @Autowired
    TbTaskService tbTaskService;

    @Override
    public Integer getCourseScore(TbElective tbElective, TbStudent tbStudent) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("classId", tbElective.getTbClass().getId());
        param.put("courseId", tbElective.getTbCourse().getId());
        param.put("termStartDate", DateUtil.format(tbElective.getTermStartDate(), DateUtil.DATE_FORMAT));
        param.put("termStopDate", DateUtil.format(tbElective.getTermStopDate(), DateUtil.DATE_FORMAT));
        // 查询出全部签到信息
        List<TbSignInfo> tbSignInfoList = tbSignInfoService.query(param);
        // 查询出全部的作业信息
        List<TbTaskInfo> tbTaskInfoList = tbTaskInfoService.query(param);

        // 计算出该生的签到成绩
        param.put("stuId", tbStudent.getId());
        Integer signScore = 100, taskScore = 100;
        List<TbSign> tbSignList = tbSignService.query(param);
        signScore = tbSignInfoList.size() == 0 ? 100 : (tbSignList.size() * 100/ tbSignInfoList.size());
        // 计算出该生的作业成绩
        List<TbTask> tbTaskList = tbTaskService.query(param);
        Integer total = 0;
        for (TbTask tbTask : tbTaskList) {
            if (null != tbTask.getScore()) {
                total += tbTask.getScore();
            }
        }
        taskScore = tbTaskInfoList.size() == 0 ? 100 : (total / tbTaskInfoList.size());

        // 签到成绩 和 作业成绩 分别占平时分的50%
        return signScore / 2 + taskScore / 2;
    }
}
