package com.redspider.pss.vo.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 举报处理记录信息
 * @author: hf
 * @create: 2021-06-06 16:16
 **/
@Data
@ApiModel(value = "举报处理记录信息")
public class RemarkDisposeVO implements Serializable {

    /**
     * 审核详情主键
     */
    @ApiModelProperty(value = "审核详情主键")
    private Long id;
    /**
     * 业务id
     */
    @ApiModelProperty(value = "业务id")
    private Long bizId;
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型")
    private Integer bizType;
    /**
     * 被举报次数
     */
    @ApiModelProperty(value = "被举报次数")
    private Integer count;
    /**
     * 审核结果状态(0 无效举报, 1 有效举报)
     */
    @ApiModelProperty(value = "审核结果状态(0 无效举报, 1 有效举报)")
    private Integer resultStatus;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    private String resultDetails;
    /**
     * 审核人id
     */
    @ApiModelProperty(value = "审核人id")
    private Long disposeId;
}
