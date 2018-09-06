/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.FileUtil;
import com.wyk.sign.web.api.param.Output;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息相关接口
 *
 * @author wyk
 */
@Controller("apiMessage")
@RequestMapping("/api/message")
public class MessageController extends AbstractController {

    /**
     * 消息类文件上传
     * <p>传入参数</p>
     *
     * <pre>
     *     file : MultipartFile文件
     *     filename：自定义的文件名
     * </pre>
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadMessageFile", method = RequestMethod.POST)
    public @ResponseBody Output uploadMessageFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
        Output result = new Output();
        String filename = request.getParameter("filename");
        String fileId = String.format("Message/%s", filename);
        try {
            String uploadUrl = uploadFile(file, fileId);
            Map<String, Object> data = new HashMap<>();
            String fileSize = FileUtil.getFileSize(file);
            data.put("size", fileSize);
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
}
