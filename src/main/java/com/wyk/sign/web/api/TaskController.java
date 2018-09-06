/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.framework.utils.FileUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TaskInfoService;
import com.wyk.sign.service.TaskService;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        result.setMsg("获取作业成功！");
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
     * params : {"infoId" : "2", "upFileUrl" : "/attachment/task_info1/201106259_109933.doc", "desc" : "英语2班王小二"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("infoId"));
        if (null == taskInfo) {
            return new Output(ERROR_NO_RECORD, "没有对应作业信息！");
        }
        Task task = new Task();
        task.setInfo(taskInfo);
        Student student = (Student) input.getCurrentUser();
        task.setStudent(student);
        if (new Date().after(taskInfo.getDeadlineTime())) {
            return new Output(ERROR_NO_RECORD, "已超过上交截止日期！");
        }
        String upFileUrl = input.getString("upFileUrl");
        logger.info("upFileUrl为：", upFileUrl);
        task.setUpFileUrl(input.getString("upFileUrl"));
        task.setDesc(input.getString("desc"));
        taskService.insert(task);
        result.setMsg("作业上交成功！");
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
     *     params : 全部信息 {"id": "1", "upFileUrl" : "/api/1099_张三.doc", "desc" : "英语2班王小二"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output modify(Input input) {
        Output result = new Output();
        Task task = taskService.get(input.getLong("id"));
        task.setUpFileUrl(input.getString("upFileUrl"));
        task.setDesc(input.getString("desc"));
        taskService.update(task);
        result.setMsg("修改作业内容成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 作业内容修改
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *     method : delete
     *     token : wxId
     *     params : 全部信息 {"id": "1"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output delete(Input input) {
        Output result = new Output();
        Task task = taskService.get(input.getLong("id"));
        if (null == task) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业！");
        }
        taskService.delete(task);
        result.setMsg("删除作业成功！");
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
        if (null == task) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业！");
        }
        task.setScore(input.getDouble("score"));
        taskService.update(task);
        result.setMsg("评分成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 上传task文件
     * <p>传入参数</p>
     * <pre>
     *     method : uploadTaskFile
     *     token : wxId
     *     params : {info : 1, file ；MultipartFile(object)}
     * </pre>
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadTaskFile", method = RequestMethod.POST)
    public @ResponseBody Output uploadTaskFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        Output result = new Output();
        String fileno = request.getParameter("fileno");
        String infoId = request.getParameter("infoId");
        String token = request.getParameter("token");
        String filename = request.getParameter("filename");
        String fileId = "";
        if(StringUtils.isNotEmpty(filename)){
            fileId = String.format("taskInfo%s/%s", infoId, filename);
        }else{
            Student student = (Student) getCurrentUserByToken(token);
            fileId = String.format("taskInfo%s/%s", infoId, student.getSno());
        }
        try {
            String uploadUrl = uploadFile(file, fileId);
            Map<String, Object> data = new HashMap<>();
            data.put("size", FileUtil.getFileSize(file));
            data.put("no", fileno);
            data.put("name", uploadUrl.substring(uploadUrl.lastIndexOf("/") + 1));
            data.put("url", uploadUrl);
            result.setMsg("文件上传成功！");
            result.setData(data);
            result.setStatus(SUCCESS);
        } catch (Exception e) {
            return new Output(ERROR_UNKNOWN, "文件上传失败！");
        }
        return result;
    }

    /**
     * 作业下载
     * <p>传入参数</p>
     *
     * <pre>
     *     filename : attachment/file/taskInfo1/201106214_tREb2q.exe
     * </pre>
     * @param filename
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downloadTaskFile")
    public ResponseEntity<byte[]> download(@RequestParam("filename") String filename) {
        return downloadFile(filename);
    }

    /**
     * 查看作业情况
     * <p>传入参数</p>
     *
     * <pre>
     *     token : wxId,
     *     method : queryTaskListByInfoId
     *     params : {"infoId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output getTaskListByInfoId(Input input) {
        Output result = new Output();
        TaskInfo taskInfo = taskInfoService.get(input.getLong("infoId"));
        if (null == taskInfo) {
            return new Output(ERROR_UNKNOWN, "没有对应的作业信息，请重试！");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("infoId", taskInfo.getId());
        List<Task> taskList = taskService.query(param);
        result.setData(toArray(taskList));
        result.setMsg("获取作业情况成功！");
        return result;
    }

    /**
     * 用于展示任务
     *
     * @param task
     * @return
     */
    public Map<String, Object> toMap(Task task) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> info = new HashMap<String, Object>();
        TaskInfo taskInfo = task.getInfo();
        info.put("id", taskInfo.getId());
        info.put("course", taskInfo.getCourse());
        info.put("classes", taskInfo.getClasses());
        info.put("deadlineTime", DateUtil.format(taskInfo.getDeadlineTime(), DateUtil.DATE_FORMAT));
        info.put("content", taskInfo.getContent());
        info.put("title", taskInfo.getTitle());
        result.put("info", info);
        result.put("upDate", DateUtil.showDateMDHM(task.getCreateTime()));
        result.put("student", task.getStudent());

        if(StringUtils.isNotEmpty(task.getUpFileUrl())){
            try {
                List<Map<String, String>> fileUrlList = objectMapper.readValue(task.getUpFileUrl(), List.class);
                result.put("upFileUrl", fileUrlList);
            } catch (IOException e) {
                logger.error("获取文件url失败！{}", e.getMessage());
            }
        }else{
            result.put("upFileUrl", task.getUpFileUrl());
        }
        result.put("upFileUrl", task.getUpFileUrl());
        result.put("desc", task.getDesc());
        result.put("score", task.getScore());
        return result;
    }
}
