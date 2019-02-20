/**
 *
 */
package com.wyk.sign.web.api;

import com.wyk.framework.utils.DateUtil;
import com.wyk.sign.annotation.Checked;
import com.wyk.sign.annotation.Item;
import com.wyk.sign.model.*;
import com.wyk.sign.service.TbElectiveService;
import com.wyk.sign.service.TbSignInfoService;
import com.wyk.sign.service.TbSignService;
import com.wyk.sign.conts.Constants;
import com.wyk.sign.web.api.param.Input;
import com.wyk.sign.web.api.param.Output;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 签到信息相关接口
 *
 * <pre>由管理者创建</pre>
 *
 * @author wyk
 */
@Controller("apiSignInfo")
@RequestMapping("/api/signInfo")
public class SignInfoController extends AbstractController {

    @Autowired
    TbSignInfoService tbSignInfoService;

    @Autowired
    TbSignService tbSignService;

    @Autowired
    TbElectiveService tbElectiveService;

    /**
     * 创建签到
     * <p>
     * 传入参数
     * </p>
     * <pre>
     * token : openid
     * method : insert
     * params : {"startDate" : "2018-06-09 10:30:00",
     *          "stopDate" : "2018-06-09 12:30:00",
     *          "address" : "xxxx大楼",
     *          "longitude" : "32.2323232",
     *          "latitude" : "135.2252525",
     *          "classId" : "02",
     *          "courseId" : "2"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output insert(Input input) {
        Output result = new Output();
        TbAdmin admin = (TbAdmin) input.getCurrentTbUser();

        if (StringUtils.isEmpty(input.getString("address"))) {
            return new Output(ERROR_NO_RECORD, "签到地址为空！");
        }
        String address = input.getString("address");
        String latitude = input.getString("latitude");
        String longitude = input.getString("longitude");
        String remark = input.getString("remark");

        TbClass TbClass = new TbClass();
        if (StringUtils.isNotEmpty(input.getString("classId"))) {
            TbClass.setId(input.getLong("classId"));
        }
        TbCourse tbCourse = new TbCourse();
        if (StringUtils.isNotEmpty(input.getString("courseId"))) {
            tbCourse.setId(input.getLong("courseId"));
        }

        if (admin.getUserType().equals(Constants.User.TEACHER) && StringUtils.isNotEmpty(input.getString("courseId"))) {
            input.getParams().put("adminId", admin.getId());
            TbElective tbElective = tbElectiveService.get(input.getParams());
            if (null == tbElective) {
                return new Output(ERROR_NO_RECORD, "没有此授课信息，请检查对应的授课课程和班级！");
            }
        }

        if (StringUtils.isNotEmpty(input.getString("signStartTime")) && StringUtils.isNotEmpty(input.getString("signStartTime"))) {
            if (DateUtil.parse(input.getString("signStopTime"), DateUtil.DATETIME_FORMAT_HM).before(DateUtil.parse(input.getString("signStopTime"), DateUtil.DATETIME_FORMAT_HM))) {
                return new Output(ERROR_NO_RECORD, "签到截止时间早于开始时间！");
            }
        } else {
            return new Output(ERROR_NO_RECORD, "签到截止时间为空！");
        }

        // 多个签到情况
        if (StringUtils.isNotEmpty(input.getString("signStartDate")) && StringUtils.isNotEmpty(input.getString("signStopDate"))) {
            if (input.getDate("signStopDate").before(input.getDate("signStartDate"))) {
                return new Output(ERROR_NO_RECORD, "签到结束日期早于开始日期！");
            }

            try {
                List<TbSignInfo> tbSignInfoList = new ArrayList<>();
                Date signStartDate = input.getDate("signStartDate");
                Date signStopDate = input.getDate("signStopDate");
                int diffDays = DateUtil.daysBetween(signStopDate, signStartDate);
                for (int i = 0; i <= diffDays; i++) {
                    TbSignInfo tbSignInfo = new TbSignInfo();
                    tbSignInfo.setAddress(address);
                    tbSignInfo.setLatitude(latitude);
                    tbSignInfo.setLongitude(longitude);
                    tbSignInfo.setRemark(remark);
                    tbSignInfo.setTbClass(TbClass);
                    tbSignInfo.setTbCourse(tbCourse);
                    tbSignInfo.setAdmin(admin);

                    Date date = DateUtil.addDay(input.getDate("signStartDate"), i);
                    String startDate = DateUtil.format(date, DateUtil.DATE_FORMAT).concat(DateUtil.BLANK).concat(input.getString("signStartTime"));
                    String stopDate = DateUtil.format(date, DateUtil.DATE_FORMAT).concat(DateUtil.BLANK).concat(input.getString("signStopTime"));
                    tbSignInfo.setStartDate(DateUtil.parse(startDate, "yyyy-MM-dd HH:mm"));
                    tbSignInfo.setStopDate(DateUtil.parse(stopDate, "yyyy-MM-dd HH:mm"));
                    tbSignInfoList.add(tbSignInfo);

                    if (i % 10 == 0 && i >= 10) {
                        tbSignInfoService.batchSave(tbSignInfoList);
                        tbSignInfoList.clear();
                    } else if (i == diffDays) {
                        tbSignInfoService.batchSave(tbSignInfoList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Output(ERROR_NO_RECORD, "批量插入失败" + e.getMessage());
            }
        } else if (StringUtils.isNotEmpty(input.getString("signDate"))) {
            TbSignInfo tbSignInfo = new TbSignInfo();
            tbSignInfo.setAddress(address);
            tbSignInfo.setLatitude(latitude);
            tbSignInfo.setLongitude(longitude);
            tbSignInfo.setRemark(remark);
            tbSignInfo.setTbClass(TbClass);
            tbSignInfo.setTbCourse(tbCourse);
            tbSignInfo.setAdmin(admin);

            String startDate = input.getString("signDate").concat(DateUtil.BLANK).concat(input.getString("signStartTime"));
            String stopDate = input.getString("signDate").concat(DateUtil.BLANK).concat(input.getString("signStopTime"));
            tbSignInfo.setStartDate(DateUtil.parse(startDate, "yyyy-MM-dd HH:mm"));
            tbSignInfo.setStopDate(DateUtil.parse(stopDate, "yyyy-MM-dd HH:mm"));
            tbSignInfoService.insert(tbSignInfo);
        } else {
            return new Output(ERROR_NO_RECORD, "签到日期为空，请重填！");
        }


        result.setMsg("创建签到成功！");
        result.setStatus(SUCCESS);
        return result;
    }

    /**
     * 计算两个时间的天数
     *
     * @param one
     * @param other
     * @return
     * @throws ParseException
     */
    public int dayDiff(String one, String other) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(one);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date otherDate = sdf.parse(other);
        Calendar otherCalender = Calendar.getInstance();
        otherCalender.setTime(otherDate);
        return (int) (calendar.getTimeInMillis() - otherCalender.getTimeInMillis()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 修改签到信息
     * <p>传入参数：</p>
     * <pre>
     *      method:modify
     *      token: openid
     *      params: {id: 1, 需要修改的信息}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.ADMIN)
    public Output modify(Input input) {
        Output result = new Output();
        TbSignInfo tbSignInfo = tbSignInfoService.get(input.getLong("id"));
        if (null == tbSignInfo) {
            return new Output(ERROR_NO_RECORD, "未获取到对应的签到信息！");
        }
        try {
            if (StringUtils.isNotEmpty(input.getString("address"))) {
                tbSignInfo.setAddress(input.getString("address"));
                // 经纬度和地址是一个整体对象
                tbSignInfo.setLongitude(input.getString("longitude"));
                tbSignInfo.setLatitude(input.getString("latitude"));
            }

            if (StringUtils.isNotEmpty(input.getString("remark"))) {
                tbSignInfo.setRemark(input.getString("remark"));
            }
            if (StringUtils.isNotEmpty(input.getString("startDate"))) {
                tbSignInfo.setStartDate(input.getDate("startDate", DateUtil.DATETIME_FORMAT_YMDHM));
            }
            if (StringUtils.isNotEmpty(input.getString("stopDate"))) {
                tbSignInfo.setStopDate(input.getDate("stopDate", DateUtil.DATETIME_FORMAT_YMDHM));
            }

            if (StringUtils.isNotEmpty(input.getString("stopDate")) && StringUtils.isNotEmpty(input.getString("startDate"))) {
                if (input.getDate("stopDate").before(input.getDate("startDate"))) {
                    return new Output(ERROR_NO_RECORD, "签到截止时间早于开始时间！");
                }
            }

            if (StringUtils.isNotEmpty(input.getString("classId"))) {
                TbClass TbClass = new TbClass();
                TbClass.setId(input.getLong("classId"));
                tbSignInfo.setTbClass(TbClass);
            }

            if (StringUtils.isNotEmpty(input.getString("courseId"))) {
                TbCourse tbCourse = new TbCourse();
                tbCourse.setId(input.getLong("courseId"));
                tbSignInfo.setTbCourse(tbCourse);
            }
            tbSignInfo.setModifyTime(new Date());
            tbSignInfoService.update(tbSignInfo);
        }catch (Exception e){
            logger.error(e, e);
        }
        result.setStatus(SUCCESS);
        result.setMsg("修改签到信息成功！");
        return result;
    }

    /**
     * 获取我的签到（管理员获取创建的签到，学生获取对应的班级签到）
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getMySignInfoList
     *     token: openid
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getMySignInfoList(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = new HashMap<>();
        if (Constants.User.STUDENT.equals(tbUser.getUserType())) {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        } else {
            if (StringUtils.isNotEmpty(input.getString("classId"))) {
                param.put("classId", input.getString("classId"));
            }
            param.put("adminId", tbUser.getId());
        }
        List<TbSignInfo> signingInfoList = tbSignInfoService.query(param);
        result.setData(toArray(signingInfoList, tbUser));
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
        TbSignInfo signingInfo = tbSignInfoService.get(input.getLong("id"));
        if (null == signingInfo) {
            return new Output(ERROR_NO_RECORD, "没有对应的签到信息！");
        }
        // 删除签到信息下，所有的签到
        Map<String, Object> param = new HashMap<>();
        param.put("infoId", input.getLong("id"));
        tbSignService.deleteByMap(param);
        tbSignInfoService.delete(signingInfo);
        result.setStatus(SUCCESS);
        result.setMsg("删除签到信息成功！");
        return result;
    }

    /**
     * 获取选中日期对应的签到信息
     *
     * <p>传入参数</p>
     * <pre>
     *     method: getSignInfoBySelectedDate
     *     token: openid
     *     params: {selectDate : "2018-07-31"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getSignInfoBySelectedDate(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = input.getParams();
        if (tbUser.getUserType().equals(Constants.User.STUDENT)) {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        } else {
            param.put("adminId", tbUser.getId());
        }
        param.put("selectDate", input.getString("selectDate"));
        List<TbSignInfo> tbSignInfoList = tbSignInfoService.query(param);
        if (null == tbSignInfoList || tbSignInfoList.size() == 0) {
            return new Output(SUCCESS, "当天无签到信息！");
        }

        result.setData(toArray(tbSignInfoList, tbUser));
        result.setStatus(SUCCESS);
        result.setMsg("获取签到信息成功！");
        return result;
    }

    /**
     * 获取当前月份的签到日期
     * <p>传入参数</p>
     *
     * <pre>
     *     token : 13381503907
     *     method : getSignInfoByCurMonth
     *     params : {selectMonth : "2018-08"}
     * </pre>
     *
     * @param input
     * @return
     */
    @Checked(Item.TYPE)
    public Output getSignDatesByCurMonth(Input input) {
        Output result = new Output();
        TbUser tbUser = input.getCurrentTbUser();
        Map<String, Object> param = input.getParams();
        if (tbUser.getUserType().equals(Constants.User.STUDENT)) {
            param.put("classId", ((TbStudent) tbUser).getTbClass().getId());
        } else {
            param.put("adminId", tbUser.getId());
        }
        param.put("curMonth", input.getString("curMonth"));
        List<String> signInfoList = tbSignInfoService.getSignDatesByCurMonth(param);
        result.setData(signInfoList);
        result.setStatus(SUCCESS);
        result.setMsg("获取当月签到日期成功！");
        return result;
    }

    /**
     * 用于展示
     *
     * @param tbSignInfo
     * @return
     */
    public Map<String, Object> toMap(TbSignInfo tbSignInfo, TbUser tbUser) {
        if (tbSignInfo == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", tbSignInfo.getId());
        result.put("signDate", DateUtil.format(tbSignInfo.getStartDate(), DateUtil.DATE_FORMAT));
        result.put("stopDate", DateUtil.format(tbSignInfo.getStopDate(), DateUtil.DATETIME_FORMAT_HM));
        result.put("startDate", DateUtil.format(tbSignInfo.getStartDate(), DateUtil.DATETIME_FORMAT_HM));
        result.put("address", tbSignInfo.getAddress());
        result.put("latitude", tbSignInfo.getLatitude());
        result.put("longitude", tbSignInfo.getLongitude());
        result.put("status", tbSignInfo.getStatus());
        result.put("admin", tbSignInfo.getAdmin());
        result.put("course", tbSignInfo.getTbCourse());
        result.put("remark", tbSignInfo.getRemark());
        // 班级信息
        Map<String, Object> classesMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("classId", tbSignInfo.getTbClass().getId());
        Map<String, Object> studentMap = new HashMap<>();
        List<TbStudent> tbStudentList = tbStudentService.query(param);
        studentMap.put(LIST, tbStudentList);
        studentMap.put(TOTAL, tbStudentList.size());
        classesMap.put("studentList", studentMap);
        classesMap.put("id", tbSignInfo.getTbClass().getId());
        classesMap.put("name", tbSignInfo.getTbClass().getName());
        result.put("classes", classesMap);
        // 已签到信息
        param = new HashMap<>();
        Map<String, Object> signDetailMap = new HashMap<>();
        param.put("infoId", tbSignInfo.getId());
        param.put("status", Constants.Sign.SIGNED);
        List<TbSign> tbSignList = tbSignService.query(param);
        signDetailMap.put(LIST, tbSignList);
        signDetailMap.put(TOTAL, tbSignList.size());
        result.put("signedList", signDetailMap);
        // 个人签到信息
        if (Constants.User.STUDENT.equals(tbUser.getUserType())) {
            param = new HashMap<>();
            param.put("infoId", tbSignInfo.getId());
            param.put("wxId", tbUser.getWxId());
            TbSign tbSign = tbSignService.get(param);
            result.put("sign", tbSign);
        }
        return result;
    }
}
