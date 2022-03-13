package com.redspider.pss.domain;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @program: pss
 * @description: 保存组队单类
 * @author: txy
 * @create: 2021-06-06 16:16
 **/
@Data
@ApiModel(value = "组队单展示")
public class PssGroupTeamQueryDO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 图片URl
     */
    private JSONArray pictureUrlArray;

    private List<String> labelIdArray;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 过期时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    /**
     * 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examineTime;

    /**
     * 最小组队人数
     */
    private int minTeamSize;
    /**
     * 最大组队人数
     */
    private int maxTeamSize;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 地址
     */
    private String place;
    /**
     * 经度
     */
    private String lon;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 发布状态 0草稿 1创建成功
     */
    private Integer releaseStatus;
    /**
     *  组队状态 0未开始 1  组队中 2组队成功 3组队失败 4撤销组队
     */
    private Integer teamStatus;
    /**
     * 确认类型 0不需要确认 1需要确认
     */
    private Integer confirmStatus;

    /**
     * 是否包含我自己 0不包含 1包含
     */
    private Integer containMe;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 创建人ID
     */
    private Long creatorId;

    private String createName;


    /**
     * 当前加入数
     */
    private Integer currentJoinNum;

    /**
     * 需要人数
     */
    private Integer needNum;

}
