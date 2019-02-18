/**
 *
 */
package com.wyk.sign.web.api;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import com.wyk.framework.utils.AesCbcUtil;
import com.wyk.framework.utils.HttpRequestUtil;
import com.wyk.sign.model.*;
import com.wyk.sign.service.*;
import com.wyk.sign.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;

/**
 * 登录注册相关接口
 *
 * @author wyk
 */
@Controller("apiLogin")
@RequestMapping("/api/login")
public class LoginController extends AbstractController {

    @Value("#{properties['wechat.appid']}")
    protected String wechatAppid;

    @Value("#{properties['wechat.secret']}")
    protected String wechatSecret;

    @Autowired
    ClassesService classesService;

    @Autowired
    SignService signService;

    @Autowired
    TaskService taskService;

    @Autowired
    SignInfoService signInfoService;

    @Autowired
    TaskInfoService taskInfoService;



    /**
     * 用户登录
     * <p>传入参数</p>
     *
     * <pre>
     *     method: login,
     *     params: {wxId: '12'}
     * </pre>
     * @param input
     * @return
     */
    public Output login(Input input){
        Output result = new Output();
        String token = input.getString("wxId");
        if(StringUtils.isEmpty(token)){
            return new Output(ERROR_UNKNOWN, "openid为空！");
        }

        TbUser tbUser = getCurrentUserByToken(token);
        if(null == tbUser){
            return new Output(ERROR_NO_RECORD, "没有此用户！");
        }

        result.setData(toMap(tbUser));
        result.setMsg("登录成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 注册个人信息
     * <p>传入参数</p>
     * <pre>
     *     method: register
     *     params: {wxId: 01,
     *     			wxName: darren,
     *     			wxAvatarUrl: http://qq.com/user/iul.pgn,
     *     			userType: 1,
     *     			realName: 张三,
     *     			sno: 20188888, // 可以为空
     *     			classId:01 // 可以为空
     *                    }
     * </pre>
     *
     * @param input
     * @return
     */
    public Output register(Input input) {
        Output result = new Output();
        Integer userType = input.getInteger("userType");
        try {
            String wxName = EmojiParser.parseToAliases(input.getString("wxName"));
            // 管理员注册
            if (userType == 1 || userType == 2) {
                TbAdmin admin = new TbAdmin();
                admin.setWxId(input.getString("wxId"));
                admin.setWxName(wxName);
                admin.setWxAvatarUrl(input.getString("wxAvatarUrl"));
                admin.setRealName(input.getString("realName"));
                admin.setUserType(userType);
                administratorService.insert(admin);
                result.setData(toMap(admin));
                // 学生注册
            } else if (userType == 3) {
                TbStudent tbStudent = new TbStudent();
                tbStudent.setWxId(input.getString("wxId"));
                tbStudent.setWxName(wxName);
                tbStudent.setWxAvatarUrl(input.getString("wxAvatarUrl"));
                tbStudent.setRealName(input.getString("realName"));
                tbStudent.setUserType(userType);
                tbStudent.setSno(input.getString("sno"));
                if (!StringUtils.isEmpty(input.getString("classId"))) {
                    TbClass TbClass = new TbClass();
                    TbClass.setId(input.getLong("classId"));
                    tbStudent.setTbClass(TbClass);
                }
                studentService.insert(tbStudent);
                result.setData(toMap(tbStudent));
            } else {
                return new Output(ERROR_UNKNOWN, "未设置【用户类型】，请重新设置！");
            }
        }catch (Exception e){
            logger.error("用户{}注册失败！{}", input.getString("realName"), e.getMessage());
            return new Output(ERROR_NO_RECORD, "注册出错！" + e.getMessage());
        }

        result.setMsg("注册成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbUser
     * @return
     */
    public static Map<String, Object> toMap(TbUser tbUser) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", tbUser.getId());
        result.put("wxId", tbUser.getWxId());
        String wxName = EmojiParser.parseToUnicode(tbUser.getWxName());
        result.put("wxName", wxName);
        result.put("wxAvatarUrl", tbUser.getWxAvatarUrl());
        result.put("realName", tbUser.getRealName());
        Integer userType = tbUser.getUserType();
        result.put("userType", userType);
        if (Constants.User.STUDENT.equals(userType)) {
            result.put("userTypeName", "学生");
            TbStudent tbStudent = (TbStudent) tbUser;
            result.put("sno", tbStudent.getSno());
            result.put("classes", tbStudent.getTbClass());
        } else if (Constants.User.TEACHER.equals(userType)) {
            result.put("userTypeName", "教师");
        } else if (Constants.User.COUNSELLOR.equals(userType)) {
            result.put("userTypeName", "辅导员");
        }
        return result;
    }

    /**
     * 登录
     * <p>传入参数</p>
     *
     * <pre>
     *     method: getUserInfo,
     *     params: {iv: '', encryptedData: '', code: ''}
     * </pre>
     * @param input
     * @return
     */
    public Output getUserInfo(Input input) {
        Output result = new Output();
        Map<String, Object> data = new HashMap<>();
        String iv = input.getString("iv");
        String encryptedData = input.getString("encryptedData");
        String code = input.getString("code");
        if (code == null || code.length() == 0) {
            return new Output(ERROR_UNKNOWN, "code 不能为空");
        }

        String params = "appid=" + wechatAppid + "&secret=" + wechatSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject json = JSONObject.parseObject(sr);
        String sessionKey = json.get("session_key").toString();
        String openId = json.get("openid").toString();
        data.put("openid", openId);
        try {
            String resultAes = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            if (null != result && resultAes.length() > 0) {
                JSONObject userInfoJSON = JSONObject.parseObject(resultAes);
                Map userInfo = new HashMap();
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                data.put("userInfo", userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录时加密出错", e.getMessage());
            return new Output(ERROR_UNKNOWN, "加密出错！" + e.getMessage());
        }
        result.setStatus(SUCCESS);
        result.setMsg("登录成功！");
        result.setData(data);
        return result;
    }

}
