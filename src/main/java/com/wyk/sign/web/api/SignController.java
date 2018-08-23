/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.SignInfoService;
import com.wyk.sign.service.SignService;
import com.wyk.sign.util.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 签到相关接口
 *
 * <pre>由学生创建</pre>
 *
 * @author wyk
 */
@Controller("apiSign")
@RequestMapping("/api/sign")
public class SignController extends AbstractController {

    @Autowired
    SignInfoService signInfoService;

    @Autowired
    SignService signService;

    /**
     * 创建签到
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
        Sign sign = signService.get(input.getLong("id"));
        if (null == sign) {
            return new Output(ERROR_NO_RECORD, "未能获取签到信息！");
        }
        result.setData(sign);
        result.setMsg("创建签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建签到
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : wxid
     * method : insert
     * params : {"infoId" : "2", "signDate" : "2018-06-09 10:30:00", "signAddress" : "xxxx大楼"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output insert(Input input) {
        Output result = new Output();
        Sign sign = new Sign();
        SignInfo signInfo = signInfoService.get(input.getLong("infoId"));
        if(null == signInfo){
            return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
        }
        sign.setInfo(signInfo);
        Student student = (Student) input.getCurrentUser();
        sign.setStudent(student);
        sign.setSignAddress(input.getString("signAddress"));

        // 签到逻辑
        if (!sign.getSignAddress().equals(signInfo.getAddress())) {
            return new Output(ERROR_UNKNOWN, "签到地点不对，请重新定位！");
        }

        if (StringUtils.isNotEmpty(input.getString("signDate"))) {
            sign.setSignDate(input.getDate("signDate", DateUtil.DATETIME_FORMAT));
        }

        if (sign.getSignDate().before(signInfo.getStartDate())) {
            return new Output(ERROR_UNKNOWN, String.format("请在%s至%s的时间段内签到！", DateUtil.format(signInfo.getStartDate(), DateUtil.DATETIME_FORMAT), DateUtil.format(signInfo.getStopDate(), DateUtil.DATETIME_FORMAT)));
        }

        if (sign.getSignDate().after(signInfo.getStopDate())) {
            result.setMsg("你的签到状态为：【未签到】！");
            sign.setStatus(Constants.Sign.NOT_SIGNED);
        }else{
            result.setMsg("你的签到状态为：【签到】！");
            sign.setStatus(Constants.Sign.SIGNED);
        }
        signService.insert(sign);
        result.setData(sign);
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 创建签到
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
    public Output delete(Input input) {
        Output result = new Output();
        Sign sign = signService.get(input.getLong("id"));
        if (null == sign) {
            return new Output(ERROR_NO_RECORD, "未能获取签到信息！");
        }
        signService.delete(sign);
        result.setMsg("删除签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 申请补签
     * <p>传入参数：</p>
     * <pre>
     *      method:suppleSign
     *      token: wxopenid
     *      params: {infoId: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.STU)
    public Output suppleSign(Input input) {
        Output result = new Output();
        Map<String, Object> param = new HashMap<>();
        Student student = (Student) input.getCurrentUser();
        param.put("wxId", student.getWxId());
        Sign sign = signService.get(param);

        if(null == sign){
            sign = new Sign();
            sign.setStudent(student);
            SignInfo info = signInfoService.get(input.getLong("infoId"));
            if(null == info){
                return new Output(ERROR_UNKNOWN, "没有对应的签到信息！");
            }
            sign.setInfo(info);
            sign.setSignDate(new Date());
        }else{
            sign.setModifyTime(new Date());
        }
        sign.setStatus(Constants.Sign.SUPPLE_SIGN);
        signService.save(sign);
        result.setStatus(SUCCESS);
        result.setMsg("申请补签成功，待教师同意！");
        result.setData(sign);
        return result;
    }

    /**
     * 同意签到
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: wxopenid
     *      params: {id: 2, isAccept: 1}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output acceptSign(Input input) {
        Output result = new Output();
        Sign sign = signService.get(input.getLong("id"));
        if(null == sign){
            return new Output(ERROR_NO_RECORD, "没有对应的签到！");
        }

        if(Constants.Sign.NOT_SIGNED.equals(input.getInteger("isAccept"))){
            sign.setStatus(Constants.Sign.NOT_SIGNED);
            result.setMsg("补签未批准！");
        }else if(Constants.Sign.SIGNED.equals(input.getInteger("isAccept"))){
            sign.setStatus(Constants.Sign.SIGNED);
            result.setMsg("补签成功！");
        }else{
            return new Output(ERROR_NO_RECORD,"请选择是否同意补签！【1：批准；2：不批准】");
        }
        signService.update(sign);
        result.setStatus(SUCCESS);
        result.setData(sign);
        return result;
    }
}
