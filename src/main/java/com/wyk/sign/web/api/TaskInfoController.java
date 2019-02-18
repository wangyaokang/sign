/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.page.Paginator;
import com.wyk.framework.utils.DateUtil;
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
     * 创建作业信息
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : openid
     * method : insert
     * params : {"courseId" : "2", "classId" : "1", "deadlineTime" : "2018-12-12", title: "第一课：有丝分裂", "content" : "带笔记本"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        try {
            TbAdmin teacher = (TbAdmin) input.getCurrentTbUser();
            if (!teacher.getUserType().equals(Constants.User.TEACHER)) {
                return new Output(ERROR_UNKNOWN, "非教师无创建作业权限！");
            }
            TbTaskInfo tbTaskInfo = new TbTaskInfo();
            tbTaskInfo.setAdmin(teacher);
            tbTaskInfo.setTitle(input.getString("title"));
            tbTaskInfo.setContent(input.getString("content"));
            if (StringUtils.isNotEmpty(input.getString("deadlineTime"))) {
                tbTaskInfo.setDeadlineTime(input.getDate("deadlineTime", DateUtil.DATE_FORMAT));
            }
            input.getParams().put("adminId", teacher.getId());
            if (StringUtils.isEmpty(input.getString("courseId")) || StringUtils.isEmpty(input.getString("classId"))) {
                return new Output(ERROR_UNKNOWN, "请选择对应的课程和班级！");
            }
            TbElective tbElective = electiveService.get(input.getParams());
            if (null == tbElective) {
                return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
            }
            TbClass TbClass = new TbClass();
            TbClass.setId(input.getLong("classId"));
            tbTaskInfo.setTbClass(TbClass);
            TbCourse tbCourse = new TbCourse();
            tbCourse.setId(input.getLong("courseId"));
            tbTaskInfo.setTbCourse(tbCourse);
            taskInfoService.insert(tbTaskInfo);
            result.setMsg("创建作业信息成功！");
            result.setStatus(SUCCESS);
        } catch (Exception e) {
            return new Output(ERROR_NO_RECORD, e.getMessage());
        }
        return result;
    }

    /**
     * 修改作业信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxId
     *      params: {id:1, 需要修改的信息}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TbTaskInfo tbTaskInfo = taskInfoService.get(input.getLong("id"));
        if (null == tbTaskInfo) {
            return new Output(ERROR_NO_RECORD, "未获取到对应的作业信息！");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("classId", input.getString("classId"));
        param.put("courseId", input.getString("courseId"));
        param.put("adminId", input.getCurrentTbUser().getId());
        TbElective tbElective = electiveService.get(param);
        if (null == tbElective) {
            return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
        }

        if (StringUtils.isNotEmpty(input.getString("title"))) {
            tbTaskInfo.setTitle(input.getString("title"));
        }

        if (StringUtils.isNotEmpty(input.getString("content"))) {
            tbTaskInfo.setContent(input.getString("content"));
        }
        if (StringUtils.isNotEmpty(input.getString("deadlineTime"))) {
            tbTaskInfo.setDeadlineTime(input.getDate("deadlineTime", DateUtil.DATE_FORMAT));
        }
        if (StringUtils.isNotEmpty(input.getString("classId"))) {
            TbClass TbClass = new TbClass();
            TbClass.setId(input.getLong("classId"));
            tbTaskInfo.setTbClass(TbClass);
        }
        if (StringUtils.isNotEmpty(input.getString("courseId"))) {
            TbCourse tbCourse = new TbCourse();
            tbCourse.setId(input.getLong("courseId"));
            tbTaskInfo.setTbCourse(tbCourse);
        }
        taskInfoService.update(tbTaskInfo);
        result.setStatus(SUCCESS);
        result.setMsg("作业信息修改成功！");
        return result;
    }

    /**
     * 根据课程获取我的作业信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getTaskInfoListByCourse
     *     token: openid
     *     params: {courseId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getTaskInfoListByCourse(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        if (Constants.User.TEACHER.equals(tbUser.getUserType())) {
            param.put("adminId", tbUser.getId());
        } else {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        }
        param.put("courseId", input.getString("courseId"));
        List<TbTaskInfo> tbTaskInfoList = taskInfoService.query(param);
        result.setData(toArray(tbTaskInfoList));
        result.setStatus(SUCCESS);
        result.setMsg("获取作业信息成功！");
        return result;
    }

    /**
     * 删除签到信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: delete
     *     token: openid
     *     params: {id: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output delete(Input input) {
        Output result = new Output();
        TbTaskInfo tbTaskInfo = taskInfoService.get(input.getLong("id"));
        if (null == tbTaskInfo) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业信息！");
        }
        // 删除作业信息下，所有的作业
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", input.getLong("id"));
        taskService.deleteByMap(param);
        taskInfoService.delete(tbTaskInfo);
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
     *
     * @param input
     * @return
     */
    public Output searchKeyWord(Input input) {
        Output result = new Output();
        if (StringUtils.isEmpty(input.getString("keyWord"))) {
            return new Output(ERROR_UNKNOWN, "没有输入对应的关键词！");
        }

        // 防止输入除keyword之外过多的参数，新建一个map
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put("keyWord", input.getString("keyWord"));
        List<TbTaskInfo> tbTaskInfoList = taskInfoService.query(input.getParams());
        result.setMsg(SUCCESS);
        result.setData(tbTaskInfoList);
        result.setMsg("搜索成功！");
        return result;
    }

    /**
     * 获取我的作业信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMyTaskInfoList,
     *     token: openid
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getMyTaskInfoList(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        if (Constants.User.STUDENT.equals(tbUser.getUserType())) {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        } else {
            param.put("adminId", tbUser.getId());
        }

        List<TbTaskInfo> tbTaskInfoList = taskInfoService.query(param);
        result.setData(toArray(tbTaskInfoList));
        result.setStatus(SUCCESS);
        result.setMsg("获取作业信息成功！");
        return result;
    }

    /**
     * 获取我的作业信息（分页）
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMySignInfoList,
     *     token: openid,
     *     params: {page: 1, rows: 10}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getTaskInfoListByWxId(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        if (Constants.User.STUDENT.equals(tbUser.getUserType())) {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        } else {
            param.put("adminId", tbUser.getId());
        }
        Integer page = input.getInteger(PAGE);
        if (page == null) {
            page = Paginator.DEFAULT_CURRENT_PAGE;
        }
        Integer rows = input.getInteger(ROWS);
        if (rows == null) {
            rows = Paginator.DEFAULT_PAGE_SIZE;
        }

        Paginator paginator = new Paginator(page, rows);
        List<TbTaskInfo> tbTaskInfoList = taskInfoService.query(param, paginator);
        Map<String, Object> data = new HashMap<>();
        data.put(LIST, toArray(tbTaskInfoList));
        data.put(PAGE, page);
        data.put(ROWS, rows);
        data.put(TOTAL, paginator.getTotalCount());
        result.setData(data);
        result.setStatus(SUCCESS);
        result.setMsg("获取作业信息成功！");
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbTaskInfo
     * @return
     */
    public Map<String, Object> toMap(TbTaskInfo tbTaskInfo) {
        if (tbTaskInfo == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", tbTaskInfo.getId());
        result.put("title", tbTaskInfo.getTitle());
        result.put("content", tbTaskInfo.getContent());
        result.put("deadlineTime", DateUtil.format(tbTaskInfo.getDeadlineTime(), DateUtil.DATE_FORMAT));
        result.put("upTime", DateUtil.showDateMD(tbTaskInfo.getDeadlineTime()));
        result.put("admin", tbTaskInfo.getAdmin());
        result.put("classes", tbTaskInfo.getTbClass());
        result.put("course", tbTaskInfo.getTbCourse());
        return result;
    }
}
