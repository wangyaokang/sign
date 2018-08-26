/**
 * 
 */
package com.wyk.sign.persistence;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.Sign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface SignMapper extends BaseMapper<Sign> {

    /**
     * 根据infoId获取班级中的全部签到
     *
     * @param infoId
     * @return
     */
    List<Sign> querySignListOfClassByInfoId(Map<String, Object> param);
}
