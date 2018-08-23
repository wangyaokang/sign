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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

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
        task.setUpDate(input.getDate("upDate", DateUtil.DATE_FORMAT));
        if (task.getUpDate().after(taskInfo.getDeadlineTime())) {
            return new Output(ERROR_NO_RECORD, "已超过上交截止日期！");
        }
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
        task.setUpDate(input.getDate("upDate", DateUtil.DATE_FORMAT));
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
        task.setScore(input.getInteger("score"));
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
        String infoId = request.getParameter("infoId");
        String token = request.getParameter("wxId");
        Student student = (Student) getCurrentUserByToken(token);
        String fileId = String.format("taskInfo%s/%s", infoId, student.getSno());
        String uploadUrl = uploadFile(file, fileId);
        result.setMsg("文件上传成功！");
        result.setData(uploadUrl);
        result.setStatus(SUCCESS);
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
     * 作业删除
     * <p>传入参数</p>
     *
     * <pre>
     *     filename : attachment/file/taskInfo1/201106214_tREb2q.exe
     * </pre>
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleteTaskFile")
    public @ResponseBody Output deleteFileUrl(@RequestParam("filename") String filename){
        Output result = new Output();
        String path = context.getRealPath("/") + filename;
        FileUtil.deleteFile(path);
        result.setStatus(SUCCESS);
        result.setMsg("文件删除成功！");
        return result;
    }

}
