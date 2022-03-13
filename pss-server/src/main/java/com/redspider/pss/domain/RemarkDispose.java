package com.redspider.pss.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @description: 举报处理记录表
 * @author: hf
 * @create: 2021-05-26 21:53
 **/
@Data
@ToString
@TableName(value="remark_dispose")
@Builder
public class RemarkDispose extends CommonDO {
    
    /**
     * 无效举报
     */
    public static final int invalid_remark = 0;
    /**
     * 有效举报
     */
    public static final int valid_remark = 1;
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
     * 被举报次数
     */
    private Integer remarkCount;
    /**
     * 审核结果(0 无效举报, 1 有效举报)
     */
    private Integer resultStatus;
    /**
     * 审核意见
     */
    private String resultDetails;
}
