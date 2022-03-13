package com.redspider.pss.vo.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 被举报业务详细信息
 * @author: hf
 * @create: 2021-06-06 16:16
 **/
@Data
@Builder
@ApiModel(value = "被举报业务详细信息")
public class RemarkBizVO implements Serializable {

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
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 图片URl
     */
    @ApiModelProperty(value = "图片URl")
    private List<String> pictureUrlArray;
    
    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String introduce;
}
