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

import java.util.Date;

/**
 * 作业相关接口
 *
 * <pre>由学生创建</pre>
 *
 * @author wyk
 */
@Controller("apiTask")
@RequestMapping("/api/task")
public class TaskController extends AbstractController {

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    TaskService taskService;

    /**
     * 创建任务
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * params : {"id" : "2018-06-09 10:30"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output info(Input input) {
        Output result = new Output();
        Task task = taskService.get(input.getLong("id"));
        if (null == task) {
            return new Output(ERROR_NO_RECORD, "未能获取作业信息！");
        }
        result.setData(task);
        result.setMsg("创建作业成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建作业
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * method : insert
     * params : {"info_id" : "2", "upFileUrl" : "/api/1099_张三.doc", "desc" : "英语2班王小二"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        Task task = new Task();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("infoId"));
        task.setTaskInfo(taskInfo);
        Student student = (Student) input.getCurrentUser();
        task.setStudent(student);
        if (StringUtils.isNotEmpty(input.getString("upDate"))) {
            task.setUpDate(input.getDate("upDate", DateUtils.DATE_FORMAT));
        }
        task.setUpDate(new Date());
        if (task.getUpDate().after(taskInfo.getDeadlineTime())) {
            return new Output(ERROR_NO_RECORD, "已超过上交截止日期！");
        }
        task.setUpFileUrl(input.getString("upFileUrl"));
        task.setDesc(input.getString("desc"));
        taskService.insert(task);
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 作业内容修改
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *     method : modify
     *     token : wxId
     *     params : 全部信息 {"id": "1", "info_id" : "2", "upFileUrl" : "/api/1099_张三.doc", "desc" : "英语2班王小二"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output modify(Input input) {
        Output result = new Output();
        Task task = taskService.get(input.getLong("id"));
        task.setUpDate(new Date());
        if (StringUtils.isNotEmpty(input.getString("upFileUrl"))) {
            task.setUpFileUrl(input.getString("upFileUrl"));
        }
        if (StringUtils.isNotEmpty(input.getString("desc"))) {
            task.setDesc(input.getString("desc"));
        }
        taskService.update(task);
        result.setMsg("修改作业内容成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 教师评分
     * <p>传入参数</p>
     * <pre>
     *     method : score
     *     token : wxId
     *     params : {id : 1, score ；90}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output score(Input input) {
        Output result = new Output();
        Task task = taskService.get(input.getLong("id"));
        task.setScore(input.getInteger("score"));
        taskService.update(task);
        result.setMsg("评分成功！");
        result.setStatus(SUCCESS);
        return result;
    }

}
