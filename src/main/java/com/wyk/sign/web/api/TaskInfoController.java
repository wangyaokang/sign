/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.util.DateUtils;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TaskInfoService;
import com.wyk.sign.service.TaskService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作业信息相关接口
 *
 * <pre>由教师创建</pre>
 *
 * @author wyk
 */
@Controller("apiTaskInfo")
@RequestMapping("/api/taskInfo")
public class TaskInfoController extends AbstractController {

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    TaskService taskService;

    /**
     * 获取作业信息
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *  method : info
     *  param：{id}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output info(Input input) {
        Output result = new Output();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("id"));
        if(null == taskInfo){
            return new Output(ERROR_NO_RECORD, "没有获取对应的作业信息！");
        }
        result.setData(taskInfo);
        result.setMsg("获取作业信息成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建作业信息
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * method : insert
     * params : {"courseId" : "2", "teacherId" : "1", "deadlineTime" : "2018-12-12", "remark" : "带笔记本"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        User teacher = input.getCurrentUser();
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTeacher(teacher);
        taskInfo.setRemark(input.getString("remark"));
        if (StringUtils.isNotEmpty(input.getString("deadlineTime"))) {
            taskInfo.setDeadlineTime(input.getDate("deadlineTime", DateUtils.DATE_FORMAT));
        }
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        taskInfo.setClasses(classes);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        taskInfo.setCourse(course);
        taskInfoService.insert(taskInfo);
        result.setMsg("创建作业信息成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 修改作业信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid
     *      params: 全部信息，例如：{id: "25", "classId" : "2", "courseId" : "2", deadlineTime : "2019-09-09", "remark" : "错了，带手机"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("id"));
        if(null == taskInfo){
            return new Output(ERROR_NO_RECORD, "未获取到对应的作业信息！");
        }
        taskInfo.setRemark(input.getString("remark"));
        if (StringUtils.isNotEmpty(input.getString("deadlineTime"))) {
            taskInfo.setDeadlineTime(input.getDate("deadlineTime", DateUtils.DATE_FORMAT));
        }
        Classes classes = new Classes();
        classes.setId(input.getLong("classId"));
        taskInfo.setClasses(classes);
        Course course = new Course();
        course.setId(input.getLong("courseId"));
        taskInfo.setCourse(course);
        taskInfoService.update(taskInfo);
        result.setStatus(SUCCESS);
        result.setMsg("修改作业信息成功！");
        result.setData(classes);
        return result;
    }

    /**
     * 获取我的作业信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMySignInfoList
     *     token: wxId
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getMyTaskInfoList(Input input){
        Output result = new Output();
        User teacher = input.getCurrentUser();
        Map<String, Object> param = new HashMap<>();
        param.put("teachId", teacher.getId());
        List<TaskInfo> signingInfoList = taskInfoService.query(param);
        result.setStatus(SUCCESS);
        result.setMsg("获取我的作业信息成功！");
        result.setData(signingInfoList);
        return result;
    }

    /**
     * 删除签到信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: delete
     *     token: wxId
     *     params: {id: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input){
        Output result = new Output();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("id"));
        // 删除签到信息下，所有的签到
        taskService.deleteByInfoId(input.getInteger("infoId"));
        taskInfoService.delete(taskInfo);
        result.setStatus(SUCCESS);
        result.setMsg("删除作业信息成功！");
        return result;
    }
}
