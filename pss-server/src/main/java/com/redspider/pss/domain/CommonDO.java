package com.redspider.pss.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目model公共父类，包含所有审计字段
 */

@Data
public class CommonDO implements Serializable {

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 更新人id
     */
    private Long updaterId;

    /**
     * 是否删除
     */
    private int deleted;
}
