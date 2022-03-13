package com.redspider.pss.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @description: 举报记录
 * @author: hf
 * @create: 2021-05-26 21:53
 **/
@Data
@ToString
@Builder
@TableName(value="remark")
public class Remark extends CommonDO {
    
    /**
     * 未审核
     */
    public static final int UN_DISPOSE = 0;
    /**
     * 已审核
     */
    public static final int DISPOSE = 1;
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 业务id
     */
    private Long bizId;
    /**
     * 业务类型 (0组队单, 1评论, 2用户)
     */
    private Integer bizType;
    /**
     * 举报理由
     */
    private String content;
    /**
     * 图片URl
     */
    private String pictureUrl;
    /**
     * 审核状态(0未审核, 1已审核)
     */
    private Integer disposeStatus;
}
