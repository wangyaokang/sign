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
import com.wyk.sign.conts.Constants;
import org.apache.commons.lang.StringUtils;
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

    /**
     * 用户登录
     * <p>传入参数</p>
     *
     * <pre>
     *     method: login,
     *     params: {openId: '12'}
     * </pre>
     * @param input
     * @return
     */
    public Output login(Input input){
        String token = input.getString("openId");
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
                tbAdminService.insert(admin);
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
                tbStudentService.insert(tbStudent);
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
        Map<String, Object> data = new HashMap<>();
        String iv = input.getString("iv");
        String encryptedData = input.getString("encryptedData");
        String code = input.getString("code");
        if (code == null || code.length() == 0) {
            return new Output(ERROR_UNKNOWN, "code 不能为空");
        }

        String params = String.format("appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", wechatAppid, wechatSecret, code);
        String sr = HttpRequestUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        JSONObject json = JSONObject.parseObject(sr);
        String sessionKey = json.get("session_key").toString();
        String openId = json.get("openid").toString();
        data.put("openid", openId);
        try {
            String resultAes = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
            if (null != resultAes && resultAes.length() > 0) {
                data.put("userInfo", resultAes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录时加密出错! {}", e.getMessage());
            return Output.getFail(Constants.ERROR_CODE, e.getMessage());
        }
        return Output.getSuccess(Constants.SUCCESS_CODE, Constants.SUCCESS_MSG, data);
    }

}
