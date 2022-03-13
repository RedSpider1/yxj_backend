package com.redspider.pss.converter;


import com.redspider.pss.domain.CommonDO;

import java.util.Date;

/**
 * @program: pss
 * @description: 添加基本属性工具类
 * @author: txy
 * @create: 2021-07-06 22:07
 **/
public class SavePropertiesUtils {

    public static <T extends CommonDO> void saveProperties(T tclass, Long userId) {

        tclass.setCreateTime(new Date());
        tclass.setUpdateTime(new Date());
        tclass.setCreatorId(userId);
        tclass.setUpdaterId(userId);
        tclass.setDeleted(0);
    }
}
