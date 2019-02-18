/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.framework.utils.FileUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.TbAdmin;
import com.wyk.sign.model.TbMessage;
import com.wyk.sign.service.MessageService;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动消息相关接口
 *
 * @author wyk
 */
@Controller("apiMessage")
@RequestMapping("/api/message")
public class MessageController extends AbstractController {

    @Autowired
    MessageService messageService;

    /**
     * 新增
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid,
     *     method: insert,
     *     params: {全部信息}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        TbMessage tbMessage = new TbMessage();
        tbMessage.setTopic(input.getString("topic"));
        tbMessage.setStatus(input.getInteger("status"));
        tbMessage.setContent(input.getString("content"));
        tbMessage.setCreator((TbAdmin) input.getCurrentTbUser());
        tbMessage.setImageUrl(input.getString("imageUrl"));
        tbMessage.setUploadFileUrl(input.getString("uploadFileUrl"));
        messageService.insert(tbMessage);
        result.setStatus(SUCCESS);
        result.setMsg("消息新增成功！");
        return result;
    }

    /**
     * 修改
     * <p>传入参数</p>
     *
     * <pre>
     *     token: openid,
     *     method: insert,
     *     params: {全部信息}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TbMessage tbMessage = messageService.get(input.getLong("id"));
        if (null == tbMessage) {
            return new Output(ERROR_UNKNOWN, "没有此活动消息");
        }
        if (StringUtils.isNotEmpty(input.getString("topic"))) {
            tbMessage.setTopic(input.getString("topic"));
        }
        if (StringUtils.isNotEmpty(input.getString("status"))) {
            tbMessage.setStatus(input.getInteger("status"));
        }
        if (StringUtils.isNotEmpty(input.getString("content"))) {
            tbMessage.setContent(input.getString("content"));
        }
        tbMessage.setCreator((TbAdmin) input.getCurrentTbUser());
        if (StringUtils.isNotEmpty(input.getString("imageUrl"))) {
            tbMessage.setImageUrl(input.getString("imageUrl"));
        }
        if (StringUtils.isNotEmpty(input.getString("uploadFileUrl"))) {
            tbMessage.setUploadFileUrl(input.getString("uploadFileUrl"));
        }
        messageService.save(tbMessage);
        result.setStatus(SUCCESS);
        result.setMsg("消息修改成功！");
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
        TbMessage tbMessage = messageService.get(input.getLong("id"));
        if (null == tbMessage) {
            return new Output(ERROR_NO_RECORD, "没有对应的作业信息！");
        }
        // 删除作业信息下，所有的附件
        if (StringUtils.isNotEmpty(input.getString("imageUrl"))) {
            FileUtil.deleteFile(input.getString("imageUrl"));
        }
        if (StringUtils.isNotEmpty(input.getString("uploadFileUrl"))) {
            try {
                List<Map<String, String>> uploadFile = objectMapper.readValue(input.getString("uploadFileUrl"), List.class);
                for (Map<String, String> fileMap : uploadFile) {
                    String fileUrlPath = fileMap.get("fileUrlPath");
                    FileUtil.deleteFile(fileUrlPath);
                }
            } catch (IOException e) {
                return new Output(ERROR_UNKNOWN, "获取附件失败，请稍后重试！");
            }
        }
        messageService.delete(tbMessage);
        result.setStatus(SUCCESS);
        result.setMsg("删除作业信息成功！");
        return result;
    }

    /**
     * 消息类文件上传
     * <p>传入参数</p>
     *
     * <pre>
     *     file : MultipartFile文件
     *     filename：自定义的文件名
     * </pre>
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadMessageFile", method = RequestMethod.POST)
    public @ResponseBody
    Output uploadMessageFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        Output result = new Output();
        String fileno = request.getParameter("fileno"); // 文件编号
        String filename = request.getParameter("filename"); // 文件名
        String fileId = String.format("TbMessage/%s", filename); // 文件相对路径
        try {
            String uploadUrl = uploadFile(file, fileId);
            Map<String, Object> data = new HashMap<>();
            String fileSize = FileUtil.getFileSize(file);
            data.put("no", fileno);
            data.put("size", fileSize);
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
     * 获取消息
     * <p>传入参数</p>
     *
     * <pre>
     *     method: getMessages
     * </pre>
     *
     * @param input
     * @return
     */
    public Output getMessages(Input input) {
        Output result = new Output();
        List<TbMessage> tbMessageList = messageService.query();
        result.setData(toArray(tbMessageList));
        result.setMsg("获取消息成功！");
        result.setStatus(SUCCESS);
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
        List<TbMessage> tbMessageList = messageService.query(keyMap);
        result.setStatus(SUCCESS);
        result.setMsg("关键字搜索成功！");
        result.setData(tbMessageList);
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbMessage
     * @return
     */
    public Map<String, Object> toMap(TbMessage tbMessage) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", tbMessage.getId());
        result.put("topic", tbMessage.getTopic());
        result.put("imageUrl", tbMessage.getImageUrl());
        result.put("uploadFileUrl", tbMessage.getUploadFileUrl());
        result.put("content", tbMessage.getContent());
        result.put("status", tbMessage.getStatus());
        result.put("createTime", DateUtil.showDateMD(tbMessage.getCreateTime()));
        result.put("creator", tbMessage.getCreator());
        return result;
    }

}
