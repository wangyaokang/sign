/**
 *
 */
package com.wyk.sign.web.api;

import com.sun.net.httpserver.Authenticator;
import com.wyk.framework.util.DateUtils;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.ElectiveService;
import com.wyk.sign.service.TaskInfoService;
import com.wyk.sign.service.TaskService;
import com.wyk.sign.util.Constants;
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

    @Autowired
    ElectiveService electiveService;

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
            return new Output(ERROR_NO_RECORD, "没有对应的作业信息！");
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
     * params : {"courseId" : "2", "classId" : "1", "deadlineTime" : "2018-12-12", "remark" : "带笔记本"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        Administrator teacher = (Administrator) input.getCurrentUser();
        if(!teacher.getUserType().equals(Constants.User.TEACHER)){
            return new Output(ERROR_UNKNOWN,"非教师无创建作业权限！");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setAdmin(teacher);
        taskInfo.setRemark(input.getString("remark"));
        if (StringUtils.isNotEmpty(input.getString("deadlineTime"))) {
            taskInfo.setDeadlineTime(input.getDate("deadlineTime", DateUtils.DATE_FORMAT));
        }
        input.getParams().put("adminId", teacher.getId());
        if(StringUtils.isEmpty(input.getString("courseId")) || StringUtils.isEmpty(input.getString("classId"))){
            return new Output(ERROR_UNKNOWN, "请选择对应的课程和班级！");
        }
        Elective elective = electiveService.get(input.getParams());
        if (null == elective) {
            return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
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
     *      params: 全部信息，例如：{id: "25", adminId: 2, "classId" : "2", "courseId" : "2", deadlineTime : "2019-09-09", "remark" : "错了，带手机"}
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

        if(StringUtils.isNotEmpty(input.getString("adminId")) && StringUtils.isNotEmpty(input.getString("courseId")) && StringUtils.isNotEmpty(input.getString("classId"))){
            return new Output(ERROR_UNKNOWN, "课程或班级为空！");
        }
        Elective elective = electiveService.get(input.getParams());
        if (null == elective) {
            return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
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
        param.put("adminId", teacher.getId());
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
        if(null == taskInfo){
            return new Output(ERROR_NO_RECORD, "没有对应的作业信息！");
        }
        // 删除作业信息下，所有的作业
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", input.getLong("id"));
        taskService.deleteByMap(param);
        taskInfoService.delete(taskInfo);
        result.setStatus(SUCCESS);
        result.setMsg("删除作业信息成功！");
        return result;
    }

    /**
     * 根据关键字搜索对应的作业信息
     * <p>传入参数</p>
     *
     * <pre>
     *     keyWord : 黄老师
     * </pre>
     * @param input
     * @return
     */
    public Output searchKeyWord(Input input){
        Output result = new Output();
        if(StringUtils.isEmpty(input.getString("keyWord"))){
           return new Output(ERROR_UNKNOWN, "没有输入对应的关键词！");
        }

        // 防止输入除keyword之外过多的参数，新建一个map
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("keyWord", input.getString("keyWord"));
        List<TaskInfo> taskInfoList = taskInfoService.query(input.getParams());
        result.setMsg(SUCCESS);
        result.setData(taskInfoList);
        return result;
    }
}
