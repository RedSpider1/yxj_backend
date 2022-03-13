package com.redspider.pss.vo.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: pss
 * @description: 保存组队单类
 * @author: txy
 * @create: 2021-06-06 16:16
 **/
@Data
@ApiModel(value = "组队单保存修改类")
public class PssGroupTeamVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

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

    @ApiModelProperty(value = "标签集合")
    private List<Long> labelIdArray;

    @ApiModelProperty(value = "标签文本集合")
    private List<String> labels;
    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String introduce;
    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    /**
     * 最小组队人数
     */
    @ApiModelProperty(value = "最小组队人数")
    private int minTeamSize;
    /**
     * 最大组队人数
     */
    @ApiModelProperty(value = "最大组队人数")
    private int maxTeamSize;
    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;
    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;
    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String place;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String lon;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String lat;
    /**
     * 是否包含我自己 0不包含 1包含
     */
    @ApiModelProperty(value = "是否包含我自己")
    private Integer containMe;
    /**
     * 发布状态 0草稿 1创建成功
     */
    @ApiModelProperty(value = "发布状态 0草稿 1创建成功")
    private Integer releaseStatus;
    /**
     * 确认类型 0不需要确认 1需要确认
     */
    @ApiModelProperty(value = "确认类型 0不需要确认 1需要确认")
    private Integer confirmStatus;
}
