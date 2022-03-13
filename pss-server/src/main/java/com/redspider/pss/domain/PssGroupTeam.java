package com.redspider.pss.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program: pss
 * @description: 组队单
 * @author: txy
 * @create: 2021-05-26 21:53
 **/
@Data
@ToString
@TableName(value="pss_group_team")
public class PssGroupTeam extends CommonDO {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片URl
     */
    private String pictureUrl;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 开始时间
     */
    private Date examineTime;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 最小组队人数
     */
    private Integer minTeamSize;
    /**
     * 最大组队人数
     */
    private Integer maxTeamSize;
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
     * 是否包含我自己 0不包含 1包含
     */
    private Integer containMe;
    /**
     * 组队状态 0未开始 1  组队中 2组队成功 3组队失败 4撤销组队
     */
    private Integer teamStatus;
    /**
     * 确认类型 0 不需要确认 1 需要确认
     */
    private Integer confirmStatus;
    /**
     * 审核类型 0 自动审核 1 手动审核
     */
    private Integer examineType;
    /**
     * 审核人
     */
    private Integer examineUser;
    /**
     * 审核状态 0 未审核 1审核成功 2审核失败
     */
    private Integer examineStatus;
    /**
     * 审核时间
     */
    private Date startTime;
    /**
     * 审核结论
     */
    private String examineMsg;



}
