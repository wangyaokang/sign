/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.framework.utils.FileUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TbTaskInfoService;
import com.wyk.sign.service.TbTaskService;
import com.wyk.sign.conts.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    TbTaskInfoService tbTaskInfoService;

    @Autowired
    TbTaskService tbTaskService;

    /**
     * 创建作业（上交作业）
     * <p>传入参数</p>
     * <pre>
     * token : openid
     * method : insert
     * params : {"infoId" : "2", upFileUrl"[{no: 1, name: '', url: '', fileUrlPath: ''}]", "desc" : "英语2班王小二"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        try{
            TbTaskInfo tbTaskInfo = tbTaskInfoService.get(input.getLong("infoId"));
            if (null == tbTaskInfo) {
                return new Output(ERROR_NO_RECORD, "没有对应作业信息！");
            }
            TbStudent tbStudent = (TbStudent) input.getCurrentTbUser();
            Map<String, Object> param = new HashMap<>();
            param.put("infoId", tbTaskInfo.getId());
            param.put("stuId", tbStudent.getId());
            TbTask tbTask = tbTaskService.get(param);
            if (null == tbTask) {
                tbTask = new TbTask();
            }
            tbTask.setTbStudent(tbStudent);
            tbTask.setInfo(tbTaskInfo);

            if (DateUtil.parse(DateUtil.format(new Date())).after(tbTaskInfo.getDeadlineTime())) {
                return new Output(ERROR_NO_RECORD, "超过截止日期！");
            }
            String upFileUrl = input.getString("upFileUrl");
            logger.info("upFileUrl为：", upFileUrl);
            tbTask.setUpFileUrl(input.getString("upFileUrl"));
            tbTask.setDesc(input.getString("desc"));
            tbTask.setStatus(Constants.Task.UPLOAD_STATUS_YJ);
            tbTask.setScore(Constants.Task.DEFAULT_SCORE);
            tbTaskService.save(tbTask);
            result.setMsg("作业上交成功！");
            result.setStatus(SUCCESS);
        }catch (Exception e){
            logger.error(e, e);
        }
        return result;
    }

    /**
     * 作业内容修改
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *     method : modify
     *     token : openid
     *     params : {id: 1, 需要修改的信息}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output modify(Input input) {
        Output result = new Output();
        TbTask tbTask = tbTaskService.get(input.getLong("id"));
        if (tbTask == null) {
            return new Output(ERROR_NO_RECORD, "没有上交作业！");
        }
        if (StringUtils.isNotEmpty(input.getString("upFileUrl"))) {
            tbTask.setUpFileUrl(input.getString("upFileUrl"));
        }
        if (StringUtils.isNotEmpty(input.getString("desc"))) {
            tbTask.setDesc(input.getString("desc"));
        }
        tbTask.setCreateTime(new Date());
        tbTaskService.update(tbTask);
        result.setMsg("修改作业成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 删除作业
     * <p>
     * 传入参数
     * </p>
     * <pre>
     *     method : delete
     *     token : openid
     *     params : 全部信息 {"id": "1"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output delete(Input input) {
        Output result = new Output();
        TbTask tbTask = tbTaskService.get(input.getLong("id"));
        if (null == tbTask) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业！");
        }
        tbTaskService.delete(tbTask);
        result.setMsg("删除作业成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 教师评分
     * <p>传入参数</p>
     * <pre>
     *     method : score
     *     token : openid
     *     params : {id : 1, score ；90}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output score(Input input) {
        Output result = new Output();
        TbTask tbTask = tbTaskService.get(input.getLong("id"));
        if (null == tbTask) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业！");
        }
        tbTask.setScore(input.getInteger("score"));
        tbTaskService.update(tbTask);
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
    public @ResponseBody
    Output uploadTaskFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        Output result = new Output();
        String fileno = request.getParameter("fileno");// 文件编号
        String infoId = request.getParameter("infoId");// 作业编号
        String token = request.getParameter("token"); //
        String filename = request.getParameter("filename");
        String fileId = "";
        if (StringUtils.isNotEmpty(filename)) {
            fileId = String.format("tbTaskInfo%s/%s", infoId, filename);
        } else {
            TbStudent tbStudent = (TbStudent) getCurrentUserByToken(token);
            fileId = String.format("tbTaskInfo%s/%s", infoId, tbStudent.getSno());
        }
        try {
            String uploadUrl = uploadFile(file, fileId);
            Map<String, Object> data = new HashMap<>();
            data.put("size", FileUtil.getFileSize(file));
            data.put("no", fileno);
            data.put("name", uploadUrl.substring(uploadUrl.lastIndexOf("/") + 1));
            data.put("url", uploadUrl);
            data.put("fileUrlPath", getFileUrlPath(uploadUrl));
            result.setMsg("文件上传成功！");
            result.setData(data);
            result.setStatus(SUCCESS);
        } catch (Exception e) {
            return new Output(ERROR_UNKNOWN, "文件上传失败！");
        }
        return result;
    }

    /**
     * 查看作业情况（排序）
     * <p>传入参数</p>
     *
     * <pre>
     *     token : wxId,
     *     method : queryTaskListByInfoId
     *     params : {"infoId" : "2", "orderBy": "asc"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getTaskListByInfoId(Input input) {
        Output result = new Output();
        TbTaskInfo tbTaskInfo = tbTaskInfoService.get(input.getLong("infoId"));
        if (null == tbTaskInfo) {
            return new Output(ERROR_UNKNOWN, "没有对应的作业信息，请重试！");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("classId", tbTaskInfo.getTbClass().getId());
        List<TbStudent> tbStudentList = tbStudentService.query(param);
        List<Map<String, Object>> taskList = new ArrayList<>();
        for (TbStudent tbStudent : tbStudentList) {
            taskList.add(toMap(tbStudent, tbTaskInfo));
        }
        if (StringUtils.isNotEmpty(input.getString("orderBy"))) {
            if (Constants.ASC.equalsIgnoreCase(input.getString("orderBy"))) {
                scoreAsc(taskList);
            } else {
                scoreDesc(taskList);
            }
        }
        result.setData(taskList);
        result.setMsg("获取作业情况成功！");
        return result;
    }

    /**
     * 用于展示任务
     *
     * @param tbStudent
     * @param tbTaskInfo
     * @return
     */
    public Map<String, Object> toMap(TbStudent tbStudent, TbTaskInfo tbTaskInfo) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 作业信息
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("id", tbTaskInfo.getId());
        info.put("course", tbTaskInfo.getTbCourse());
        info.put("classes", tbTaskInfo.getTbClass());
        info.put("deadlineTime", DateUtil.format(tbTaskInfo.getDeadlineTime(), DateUtil.DATE_FORMAT));
        info.put("content", tbTaskInfo.getContent());
        info.put("title", tbTaskInfo.getTitle());
        result.put("info", info);

        // 作业
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", tbTaskInfo.getId());
        param.put("stuId", tbStudent.getId());
        TbTask tbTask = tbTaskService.get(param);
        if (tbTask == null) {
            result.put("upFileUrl", null);
            result.put("desc", null);
            result.put("score", Constants.Task.DEFAULT_MIN_SCORE);
            result.put("createTime", null);
            result.put("status", Constants.Task.UPLOAD_STATUS_WJ);
        } else {
            result.put("id", tbTask.getId());
            result.put("upFileUrl", tbTask.getUpFileUrl());
            result.put("desc", tbTask.getDesc());
            result.put("score", tbTask.getScore());
            result.put("upTime", DateUtil.showDateMD(tbTask.getCreateTime()));
            result.put("createTime", tbTask.getCreateTime());
            result.put("status", Constants.Task.UPLOAD_STATUS_YJ);
        }
        result.put("student", tbStudent);
        return result;
    }

    /**
     * 倒序（按创建时间）
     *
     * @param list
     */
    public void scoreDesc(List<Map<String, Object>> list) {
        this.scoreAsc(list);
        Collections.reverse(list);
    }

    /**
     * 正序（按创建时间）
     *
     * @param list
     */
    public void scoreAsc(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer in1 = (Integer) o1.get("score");
                Integer in2 = (Integer) o2.get("score");
                if (in1 != null && in1 != null) {
                    if (in1 > in2) {
                        return 1;
                    } else if (in1 < in2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }

                if (in1 == null && in2 == null) {
                    return 0;
                }
                if (in1 == null) {
                    return -1;
                }
                if (in2 == null) {
                    return 1;
                }
                return 0;
            }
        });
    }
}
