package com.redspider.pss.vo.team;

import com.redspider.pss.dto.group.ResourceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @program: pss
 * @description: 加入组队单
 * @author: txy
 * @create: 2021-07-24 21:00
 **/
@Data
@ApiModel(value = "加入组队单")
public class PssGroupTeamUserVO implements Serializable {


    /**
     * 组队单ID
     */
    @NotNull(message = "组队单ID不能为null")
    @ApiModelProperty(value = "组队单ID")
    private Long teamId;

    /**
     * 联系方式类型
     */
    @NotNull(message = "联系方式类型不能为null")
    @ApiModelProperty(value = "联系方式类型 0电话 1微信")
    private Integer type;

    @NotNull(message = "联系方式值不能为空")
    @ApiModelProperty("联系方式")
    private String contactInfo;

    /**
     * 备注：后续转为user_group中的comment
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 此处拿到的资源路径会先存入资源表，后存入user_group中的resourceIds中
     * 此处的资源路径为','分隔的字符串
     */
    @ApiModelProperty(value = "资源路径，包括但不限于图片")
    private List<String> resources;

    @ApiModelProperty(value = "图片上传时使用的ossKey")
    private String ossKey;

    public PssGroupTeamUserVO(Long teamId, Integer type, String remarks) {
        this.teamId = teamId;
        this.type = type;
        this.remarks = remarks;
    }

    public PssGroupTeamUserVO() {
    }
}
