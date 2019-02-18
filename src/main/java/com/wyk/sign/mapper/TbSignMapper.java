/**
 * 
 */
package com.wyk.sign.mapper;

import com.wyk.framework.mybatis.BaseMapper;
import com.wyk.sign.model.TbSign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author wyk
 *
 */
@Repository
public interface TbSignMapper extends BaseMapper<TbSign> {

    /**
     * 根据infoId获取班级中的全部签到
     *
     * @param infoId
     * @return
     */
    List<TbSign> querySignListOfClassByInfoId(Map<String, Object> param);
}
