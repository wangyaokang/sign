/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.service.TaskInfoService;
import com.wyk.sign.service.TaskService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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
    TaskService signService;

    /**
     * 获取任务信息
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

        result.setMsg("获取签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建任务
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * method : insert
     * params : {"startDate" : "2018-06-09 10:30", "stopDate" : "2018-06-09 12:30", "address" : "xxxx大楼", "classId" : "02", "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        result.setMsg("创建签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 修改任务信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid, 微信wxid
     *      params: 全部信息，例如：{id: "25", "startDate" : "2018-06-09 10:30", "stopDate" : "2018-06-09 12:30", "address" : "xxxx大楼", "classId" : "02", "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();

        result.setStatus(SUCCESS);
        result.setMsg("修改签到信息成功！");
        return result;
    }

    /**
     * 获取我的任务信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMyTaskInfoList
     *     token: wxId
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getMyTaskInfoList(Input input){
        Output result = new Output();
        result.setStatus(SUCCESS);
        result.setMsg("获取签到信息成功！");
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
        result.setStatus(SUCCESS);
        result.setMsg("删除签到信息成功！");
        return result;
    }
}
